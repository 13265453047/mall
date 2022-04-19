# 记录一次InputStream读取中文乱码的情况

得出比较好的规范用法：
1. File file = new File ("hello.txt");
   FileInputStream in=new FileInputStream (file);
2. File file = new File ("hello.txt");
   FileInputStream in=new FileInputStream (file);
   InputStreamReader inReader=new InputStreamReader (in,"UTF-8");
   BufferedReader bufReader=new BufferedReader(inReader);
3. File file = new File ("hello.txt");
   FileReader fileReader=new FileReader(file);

   BufferedReader bufReader=new BufferedReader(fileReader);
   
   
>总之，如果处理纯文本文件，建议使用FileReader，因为更方便（效率更高），也更适合阅读；但是要注意编码问题！
>其他情况（处理非纯文本文件），FileInputStream是唯一的选择；
>FileInputStream是进Socket通讯时会用到很多，如将文件流是Stream的方式传向服务器！


InputStream是表示字节输入流的所有类的超类(byte数组)
Reader是用于读取字符流的抽象类（char数组或者string）
InputStream提供的是字节流的读取，而非文本读取，这是和Reader类的根本区别。


# 读到半个中文导致的乱码问题
https://blog.csdn.net/aiben2024/article/details/102356350?spm=1001.2101.3001.6650.2&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2.pc_relevant_antiscanv2&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2.pc_relevant_antiscanv2&utm_relevant_index=5
# 一、问题的发现与分析
## 针对这个乱码问题
1. 数据库表里面字符集设置错误
2. 由于未加编码过滤器导致SpringMVC接收参数时造成的乱码
3. 代码中涉及byte数组转换String时出现了问题

>发现了一个InputStream流转成String字符串的代码有bug，会导致出现乱码
```java
    /**
	 * 将流中的内容转换为字符串，主要用于提取request请求的中requestBody
	 * @param in
	 * @param encoding
	 * @return
	 */
	public static String streamToString(InputStream in, String encoding){
		// 将流转换为字符串
		try {
			StringBuffer sb = new StringBuffer();
			byte[] b = new byte[1024];
			for (int n; (n = in.read(b)) != -1;) {
				sb.append(new String(b, 0, n, encoding));
			}
			return sb.toString();
		}  catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("提取 requestBody 异常", e);
		}
	}
```

## 分析
这段代码是一个字节流读取内容，然后转换成String的过程。
仔细观察他这段代码，发现将流的内容读取进来是采用小数组的方式。
小数组读取的方式本身没什么问题，但是下面的这个new String这个代码就有大问题了。java中utf-8编码的中文是占3个字节。
如果刚好有一个中文"我"字处于流中的位置为第1023-1025字节，那么采用小数组方式第一次读取时只读到了这个"我"字的2/3，把这2/3转成String时就产生了乱码。
因此，根本原因是用小数组方式会出现读到半个中文，然后把这个半个中文转成String就会乱码。
要解决这个问题，只需要将所有数据都读进来，最后再转换成String即可。


# 二、问题的解决
经过上面的分析，我们知道如果要保证不出现乱码则必须将流数据全部读取完毕再转换成String。
为了实现这个功能，那这个byte小数组怎么合并呢？
一次性全部读进来感觉也不是很好的方案。
这时候轮到内存输出流ByteArrayOutputStream登场了。具体的直接看下面代码
```java
	/**
	 * 将流中的内容转换为字符串，主要用于提取request请求的中requestBody
	 * @param in
	 * @param encoding
	 * @return
	 */
	public static String streamToString(InputStream in, String encoding){
		// 将流转换为字符串
		ByteArrayOutputStream bos = null;
		try {
			// 1.创建内存输出流,将读到的数据写到内存输出流中
	        bos = new ByteArrayOutputStream();
	        // 2.创建字节数组
	        byte[] arr = new byte[1024];
	        int len;
			while(-1 != (len = in.read(arr))) {
				bos.write(arr, 0, len);
			}
			// 3.将内存输出流的数据全部转换为字符串
			return bos.toString(encoding);
		}  catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("提取 requestBody 异常", e);
		} finally {
			if(null != bos) {
				try {
					// 其实这个内存输出流可关可不关，因为它的close方法里面没做任何操作。
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
```
# 三、小结
在将字节流内容转换成字符串时，特别要注意这种读取到半个中文的问题。