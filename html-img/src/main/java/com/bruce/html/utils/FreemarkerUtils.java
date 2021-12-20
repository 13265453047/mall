package com.bruce.html.utils;

import com.bruce.html.HtmlImgApplication;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.w3c.dom.Document;
import org.xhtmlrenderer.swing.Java2DRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

/**
 * @author rcy
 * @version 1.0.0
 * @className: FreemarkerUtils
 * @date 2021-12-08 9:10
 */
public class FreemarkerUtils {

    public static String getTemplate(String template, Map<String, Object> map) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
        // 这种方式再服务器上无法读取
        // String templatePath = FreemarkerUtils.class.getResource("/").getPath() + "/templates";
        // cfg.setDirectoryForTemplateLoading(new File(templatePath));

        cfg.setClassForTemplateLoading(HtmlImgApplication.class, "/templates");

        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        Template temp = cfg.getTemplate(template);
//        StringWriter stringWriter = new StringWriter();
//        temp.process(map, stringWriter);
//        stringWriter.flush();
//        stringWriter.close();

        // 填充数据后保存html文件
        String fileName = "E:/" + UUID.randomUUID().toString().replaceAll("-", "") + ".html";
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName)), StandardCharsets.UTF_8))) {
            temp.process(map, writer);
            writer.flush();
        }
        return fileName;
    }

    /**
     * 获取销售合同图片流
     *
     * @param template:
     * @param map:
     * @author: rcy
     * @date: 2021-12-08 14:12:20
     * @return: java.io.InputStream
     **/
    public static BufferedImage getImageStream(String template, Map<String, Object> map) throws Exception {
        String html = getTemplate(template, map);
        byte[] bytes = html.getBytes();
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(bin);
        Java2DRenderer renderer = new Java2DRenderer(document, 1200, -1);
        return renderer.getImage();
    }

    /*public static BufferedImage getImageStream(String template, Map<String, Object> map) throws Exception {
        String html = getTemplate(template, map);
        byte[] bytes = html.getBytes();
        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(bin);
        Java2DRenderer renderer = new Java2DRenderer(document, 1200, -1);
        return renderer.getImage();
    }*/

    /*public static BufferedImage getImageStream(String template, Map<String, Object> map) throws Exception {
        String html = getTemplate(template, map);

        byte[] bytes = html.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);


        com.spire.doc.Document doc = new com.spire.doc.Document();
        doc.loadFromStream(inputStream, FileFormat.Html);
        BufferedImage image= doc.saveToImages(0, ImageType.Bitmap);

        File file= new File("E:/ToPNG.png");
        ImageIO.write(image, "PNG", file);

        return image;
    }*/

    /*public static BufferedImage getImageStream(String template, Map<String, Object> map) throws Exception {
        String html = getTemplate(template, map);
        JEditorPane ed = new JEditorPane();
        ed.setText(html);
        ed.setSize(1000, 1000);

        //create a new image
        BufferedImage image = new BufferedImage(ed.getWidth(), ed.getHeight(), BufferedImage.TYPE_INT_ARGB);

        //paint the editor onto the image
        SwingUtilities.paintComponent(image.createGraphics(),
                ed,
                new JPanel(),
                0, 0, image.getWidth(), image.getHeight());

        return image;
    }*/

   /* private static String getTemplate(String template, Map<String, Object> map) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
        String templatePath = FreemarkerUtils.class.getResource("/").getPath() + "/templates";
        cfg.setDirectoryForTemplateLoading(new File(templatePath));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        Template temp = cfg.getTemplate(template);
        StringWriter stringWriter = new StringWriter();

//        String fileName = "doc_" + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()) + ".doc";//生成的word名称
//        String filePath = "E:/";//生成word存储路径
//
//        File outFile = new File(filePath + fileName);
//        BufferedWriter out =  new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));

        temp.process(map, stringWriter);
//        temp.process(map, out);
        stringWriter.flush();
        stringWriter.close();

//        out.flush();
//        out.close();

        return stringWriter.getBuffer().toString();
    }*/

}
