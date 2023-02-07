package com.bruce.netty.io.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射缓冲区
 *
 * @author rcy
 * @version 1.0.0
 * @className: MappedByteBufferDemo
 * @date 2023-02-07
 */
public class MappedByteBufferDemo {

    /**
     * 我们要将一个 FileChannel (它的全部或者部分)映射到内存中。为此我们将使用 FileChannel.map() 方法。
     */
    public static void main(String[] args) throws Exception {
        // 如果只需要读时可以使用FileInputStream，写映射文件时一定要使用随机( RandomAccessFile)访问文件。

//        FileInputStream fin = new FileInputStream("doc/readandshow.txt");

        RandomAccessFile fin = new RandomAccessFile("doc/readandshow.txt", "rw");
        FileChannel fc = fin.getChannel();
        // 下面代码行将文件的前 1024 个字节映射到内存中：
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, 1024);

        String msg = "Hello China, I'm Tom.";
        byte[] bytes = msg.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            mbb.put(i, bytes[i]);
            mbb.force();
        }
        fc.close();

//        char a = '中';
//        char aa = 1;
//        short b = 12;
//
//        byte c = 'a';
//        byte d = 1;
    }

    /**
     * FileChannel fileChannel = FileChannel.open(Paths.get(URI.create("file:/tmp/test/test.log")),
     *                 StandardOpenOption.WRITE, StandardOpenOption.READ);
     *       MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096);
     *       fileChannel.close();
     *       mappedByteBuffer.position(1024);
     *       mappedByteBuffer.putLong(10000L);
     *       mappedByteBuffer.force();
     *
     *
     * 上面代码的功能是通过FileChannel将文件[0~4096)区域映射到内存中，调用FileChannel的map方法返回MappedByteBuffer，
     * 在映射之后关闭通道，随后在指定位置处写入一个8字节的long类型整数，最后调用force方法将写入数据从内存写回磁盘（刷盘）。
     *
     * 映射一旦建立了，就不依赖于用于创建它的文件通道，因此在创建MappedByteBuffer之后我们就可以关闭通道了，对映射的有效性没有影响。
     *
     * 实际上将文件映射到内存比通过read、write系统调用方法读取或写入几十KB的数据要昂贵，
     * 从性能的角度来看，MappedByteBuffer适合用于将大文件映射到内存中，如上百M、上GB的大文件。
     */


    /**
     * FileChannel的map方法有三个参数：
     *
     * MapMode：映射模式，可取值有READ_ONLY（只读映射）、READ_WRITE（读写映射）、PRIVATE（私有映射），
     * READ_ONLY只支持读，READ_WRITE支持读写，而PRIVATE只支持在内存中修改，不会写回磁盘；
     *
     * position和size：映射区域，可以是整个文件，也可以是文件的某一部分，单位为字节。
     *
     *
     * 需要注意的是，如果FileChannel是只读模式，那么map方法的映射模式就不能指定为READ_WRITE。
     * 如果文件是刚刚创建的，只要映射成功，文件的大小就会变成（0+position+size）。
     *
     * mmap绕过了read、write系统函数调用，绕过了一次数据从内核空间到用户空间的拷贝，即实现零拷贝，MappedByteBuffer使用直接内存而非JVM的堆内存。
     *
     * https://blog.csdn.net/baidu_28523317/article/details/114275132?spm=1001.2101.3001.6650.4&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-4-114275132-blog-77816957.pc_relevant_multi_platform_whitelistv3&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-4-114275132-blog-77816957.pc_relevant_multi_platform_whitelistv3&utm_relevant_index=8
     *
     */
}
