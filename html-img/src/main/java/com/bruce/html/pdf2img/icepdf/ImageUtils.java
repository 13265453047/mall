package com.bruce.html.pdf2img.icepdf;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author rcy
 * @version 1.0.0
 * @className: ImageUtils
 * @date 2021-12-13 13:47
 */

/**
 * @description: PDF转换为图片的工具类
 */
@Component
public class ImageUtils {

    /**
     * 图片文件格式
     */
    public static final String FORMAT_NAME = "png";
    /**
     * 图片文件后缀名
     */
    public static final String PNG_SUFFIX = ".png";
    /**
     * 压缩文件后缀名
     */
    public static final String ZIP_SUFFIX = ".zip";

    public static final String FILETYPE_JPG = "jpg";

    /**
     * 将指定的pdf文件转换为指定路径的图片
     *
     * @param filepath  原文件路径，例如d:/test/test.pdf
     * @param imagepath 图片生成路径，例如 d:/test/
     * @param zoom      缩略图显示倍数，1表示不缩放，0.3则缩小到30%
     */
    public static void tranfer(String filepath, String imagepath, float zoom) throws Exception {
        Document document = null;
        float rotation = 0f;
        document = new Document();
        document.setFile(filepath);
        /*int maxPages = document.getPageTree().getNumberOfPages();
        for (int i = 0; i < maxPages; i++) {
            BufferedImage img = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, zoom);
            Iterator iter = ImageIO.getImageWritersBySuffix(FILETYPE_JPG);
            ImageWriter writer = (ImageWriter) iter.next();
            File outFile = new File(imagepath + new File(filepath).getName() + "_" + new DecimalFormat("000").format(i) + "." + FILETYPE_JPG);
            FileOutputStream out = new FileOutputStream(outFile);
            ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
            writer.setOutput(outImage);
            writer.write(new IIOImage(img, null, null));
        }*/

        List<BufferedImage> piclist = new ArrayList<>();
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
                    Page.BOUNDARY_CROPBOX, 0F, 2.5F);
//            String fileName = "F:/wkhtmltopdf/" + UUID.randomUUID().toString().replaceAll("-", "") + PNG_SUFFIX;
//            File imageFile = new File(fileName);
//            ImageIO.write(image, FORMAT_NAME, imageFile);
//            image.flush();

            piclist.add(image);
        }


        String imgPath = "F:/wkhtmltopdf/" + UUID.randomUUID().toString().replaceAll("-", "") + PNG_SUFFIX;
        yPic(piclist, imgPath);

        document.dispose();
        System.out.println("转换完成");
    }

    /**
     * 将宽度相同的图片，竖向追加在一起 ##注意：宽度必须相同
     *
     * @param piclist 文件流数组
     * @param outPath 输出路径
     */
    public static void yPic(List<BufferedImage> piclist, String outPath) {// 纵向处理图片
        if (piclist == null || piclist.size() <= 0) {
            System.out.println("图片数组为空!");
            return;
        }
        try {
            int height = 0, // 总高度
                    width = 0, // 总宽度
                    _height = 0, // 临时的高度 , 或保存偏移高度
                    __height = 0, // 临时的高度，主要保存每个高度
                    picNum = piclist.size();// 图片的数量
            int[] heightArray = new int[picNum]; // 保存每个文件的高度
            BufferedImage buffer = null; // 保存图片流
            List<int[]> imgRGB = new ArrayList<int[]>(); // 保存所有的图片的RGB
            int[] _imgRGB; // 保存一张图片中的RGB数据
            for (int i = 0; i < picNum; i++) {
                buffer = piclist.get(i);
                heightArray[i] = _height = buffer.getHeight();// 图片高度
                if (i == 0) {
                    width = buffer.getWidth();// 图片宽度
                }
                height += _height; // 获取总高度
                _imgRGB = new int[width * _height];// 从图片中读取RGB
                _imgRGB = buffer
                        .getRGB(0, 0, width, _height, _imgRGB, 0, width);
                imgRGB.add(_imgRGB);
            }
            _height = 0; // 设置偏移高度为0
            // 生成新图片
            BufferedImage imageResult = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_BGR);
            for (int i = 0; i < picNum; i++) {
                __height = heightArray[i];
                if (i != 0)
                    _height += __height; // 计算偏移高度
                imageResult.setRGB(0, _height, width, __height, imgRGB.get(i),
                        0, width); // 写入流中
            }

            File outFile = new File(outPath);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(imageResult, FORMAT_NAME, out);// 写图片
            byte[] b = out.toByteArray();
            FileOutputStream output = new FileOutputStream(outFile);
            output.write(b);
            out.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对外的开放接口，用于将PDF文件转换为图片文件压缩包进行下载
     *
     * @param file     SpringMVC获取的图片文件
     * @param response
     * @throws Exception
     */
    public static void pdfToImage(MultipartFile file, HttpServletResponse response) throws Exception {
        File zipFile = generateImageFile(file);
        downloadZipFile(zipFile, response);
    }

    /**
     * 将PDF文件转换为多张图片并放入一个压缩包中
     *
     * @param file SpringMVC获取的图片文件
     * @return 图片文件压缩包
     * @throws Exception 抛出异常
     */
    private static File generateImageFile(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        Document document = new Document();
        document.setByteArray(file.getBytes(), 0, file.getBytes().length, fileName);
        List<File> fileList = new ArrayList<>();
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
                    Page.BOUNDARY_CROPBOX, 0F, 2.5F);
            File imageFile = new File((i + 1) + PNG_SUFFIX);
            ImageIO.write(image, FORMAT_NAME, imageFile);
            image.flush();
            fileList.add(imageFile);
        }
        document.dispose();

        String directoryName = fileName.substring(0, fileName.lastIndexOf("."));
        File zipFile = new File(directoryName + ZIP_SUFFIX);
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
        zipFile(fileList, zos);
        zos.close();
        return zipFile;
    }

    /**
     * 下载zip文件
     *
     * @param zipFile  zip压缩文件
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    private static void downloadZipFile(File zipFile, HttpServletResponse response) throws IOException {
        FileInputStream fis = new FileInputStream(zipFile);
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        fis.close();

        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(zipFile.getName(), "UTF-8"));
        OutputStream out = response.getOutputStream();
        out.write(bytes);
        out.flush();
        out.close();
        zipFile.delete();
    }

    /**
     * 压缩文件
     *
     * @param inputFiles 具体需要压缩的文件集合
     * @param zos        ZipOutputStream对象
     * @throws IOException IO异常
     */
    private static void zipFile(List<File> inputFiles, ZipOutputStream zos) throws IOException {
        byte[] buffer = new byte[1024];
        for (File file : inputFiles) {
            if (file.exists()) {
                if (file.isFile()) {
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    int size = 0;
                    while ((size = bis.read(buffer)) > 0) {
                        zos.write(buffer, 0, size);
                    }
                    zos.closeEntry();
                    bis.close();
                    file.delete();
                } else {
                    File[] files = file.listFiles();
                    List<File> childrenFileList = Arrays.asList(files);
                    zipFile(childrenFileList, zos);
                }
            }
        }
    }

}
