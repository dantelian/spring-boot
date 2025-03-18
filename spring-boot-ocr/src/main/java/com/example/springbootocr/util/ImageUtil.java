package com.example.springbootocr.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {

    /**
     * 图像预处理
     * @param image
     * @return
     */
    public static BufferedImage imagePreprocessing(BufferedImage image) {
        // 转换为灰度图像
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = grayImage.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        // 二值化处理
        for (int y = 0; y < grayImage.getHeight(); y++) {
            for (int x = 0; x < grayImage.getWidth(); x++) {
                int rgb = grayImage.getRGB(x, y);
                int gray = (rgb & 0xff);
                gray = gray > 128 ? 255 : 0;
                grayImage.setRGB(x, y, (gray << 16) | (gray << 8) | gray);
            }
        }

        return grayImage;
    }

    public static BufferedImage preprocessImage(BufferedImage image) throws Exception {
        // 灰度化
        BufferedImage grayImage = new BufferedImage(
                image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        grayImage.getGraphics().drawImage(image, 0, 0, null);

        // 二值化（简单阈值处理）
        BufferedImage binaryImage = new BufferedImage(
                grayImage.getWidth(), grayImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        binaryImage.getGraphics().drawImage(grayImage, 0, 0, null);

        return binaryImage;
    }

}
