package com.example.springbootcommon.common.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    /**
     * 压缩图像
     * @param image
     * @param targetSizeKB  目标大小
     * @return
     */
    public static BufferedImage compressImage(BufferedImage image, int targetSizeKB) {
        // 计算目标图片的尺寸
        long targetSizeBytes = targetSizeKB * 1024;
        long originalSizeBytes = getImageSize(image);
        double compressionRatio = (double) targetSizeBytes / originalSizeBytes;
        int targetWidth = (int) (image.getWidth() * Math.sqrt(compressionRatio));
        int targetHeight = (int) (image.getHeight() * Math.sqrt(compressionRatio));
        // 使用ImageIO进行压缩
        BufferedImage compressedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        compressedImage.getGraphics().drawImage(image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);
        return compressedImage;
    }

    /**
     * 获取图片大小
     * @param image
     * @return
     */
    public static long getImageSize(BufferedImage image) {
        File tempFile;
        try {
            tempFile = File.createTempFile("temp", ".tmp");
            ImageIO.write(image, "jpg", tempFile);
            long size = tempFile.length();
            tempFile.delete();
            return size;
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * 判断文件是否未图片
     * @param file
     * @return
     */
    public static boolean isImage(MultipartFile file) {
        try {
            byte[] fileHeader = new byte[4];
            file.getInputStream().read(fileHeader, 0, 4);

            // 定义图片文件头信息
            byte[] jpegHeader = new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
            byte[] pngHeader = new byte[]{(byte) 0x89, 'P', 'N', 'G'};
            byte[] gifHeader = new byte[]{'G', 'I', 'F'};
            byte[] bmpHeader = new byte[]{'B', 'M'};

            // 比较文件头信息
            return (startsWith(fileHeader, jpegHeader) ||
                    startsWith(fileHeader, pngHeader) ||
                    startsWith(fileHeader, gifHeader) ||
                    startsWith(fileHeader, bmpHeader));
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean startsWith(byte[] array, byte[] pattern) {
        for (int i = 0; i < pattern.length; i++) {
            if (array[i] != pattern[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // 压缩图片
        try {
            String inputImagePath = "G:\\测试.jpg"; // 输入图片路径
            String outputImagePath = "G:\\测试1.jpg"; // 输出图片路径
            int targetSizeKB = 400; // 目标压缩大小（KB）
            BufferedImage image = ImageIO.read(new File(inputImagePath));
            BufferedImage compressedImage = compressImage(image, targetSizeKB);
            ImageIO.write(compressedImage, "jpg", new File(outputImagePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
