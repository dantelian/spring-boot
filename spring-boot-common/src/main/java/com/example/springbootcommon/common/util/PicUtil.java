package com.example.springbootcommon.common.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PicUtil {
    public static void main(String[] args) {
        // 压缩图片
        try {
            String inputImagePath = "G:\\测试.jpg"; // 输入图片路径
            String outputImagePath = "G:\\测试1.jpg"; // 输出图片路径
            int targetSizeKB = 400; // 目标压缩大小（KB）
            BufferedImage image = ImageIO.read(new File(inputImagePath));
            Image compressedImage = compressImage(image, targetSizeKB);
            ImageIO.write((BufferedImage) compressedImage, "jpg", new File(outputImagePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static Image compressImage(BufferedImage image, int targetSizeKB) {
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
}
