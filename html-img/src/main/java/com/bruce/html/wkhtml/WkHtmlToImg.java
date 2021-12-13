package com.bruce.html.wkhtml;

/**
 * @author rcy
 * @version 1.0.0
 * @className: Wkhtmltopdf
 * @date 2021-12-13 11:12
 */
public class WkHtmlToImg {

    //wkhtmltoimg在系统中的路径
    private static final String toImgTool = "D:\\tools\\wkhtmltopdf\\bin\\wkhtmltoimage.exe";

    public static String getCommand(String sourceFilePath, String targetFilePath) {
        String system = System.getProperty("os.name");
        if (system.contains("Windows")) {
            return toImgTool+" " + sourceFilePath + " " + targetFilePath;
        }
//        else if (system.contains("Linux")) {
//            return "wkhtmltoimage " + sourceFilePath + " " + targetFilePath;
//        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        String command = getCommand("e:/aa.html", "e:/result.jpg");
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor(); //这个调用比较关键，就是等当前命令执行完成后再往下执行

        System.out.println("执行完成");
    }

}
