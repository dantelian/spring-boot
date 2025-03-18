package com.example.springbootocr.test;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TesseractTest {

    public static void main(String[] args) {
        System.out.println(ocr());
    }

    private static String ocr() {
        String imgPath = "G:\\测试\\cs4.png";

        // 字库
//        String tessDataPath = "E:\\workspace\\gitlab\\tessdata_best-main";
        String tessDataPath = "src/main/resources/traineddata";

        String result = null;
        try {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(tessDataPath);

            // 可以选择设置 OCR 语言模型
            // chi_sim  中文简体
            // chi_tra  中文繁体
            // eng  英文
//            tesseract.setLanguage("chi_sim+eng");
            tesseract.setLanguage("chi_sim");

            // 设置识别引擎
//            tesseract.setOcrEngineMode(1);

            BufferedImage image = ImageIO.read(new File(imgPath));
            // 图像预处理
//            image = ImageUtil.imagePreprocessing(image);
//            image = ImageUtil.preprocessImage(image);

            // 进行 OCR 识别
            result = tesseract.doOCR(image);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
    }

}
