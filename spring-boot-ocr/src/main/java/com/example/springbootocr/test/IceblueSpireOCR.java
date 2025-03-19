package com.example.springbootocr.test;

import com.spire.ocr.OcrException;
import com.spire.ocr.OcrScanner;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class IceblueSpireOCR {

    public static void main(String[] args) throws OcrException, IOException {
        ocr2();
    }

    private static void ocr1(MultipartFile file) {
        // 创建临时文件
        File tfile = null;
        try {
            tfile = File.createTempFile("tempfile", file.getOriginalFilename());
            // 写入数据
            file.transferTo(tfile);
            String imageFile = tfile.getPath();

            URL resourceURL = ClassLoader.getSystemResource("dependencies");
            String dependence = resourceURL.getPath();
            OcrScanner scanner = new OcrScanner();
            scanner.setDependencies(dependence);
            scanner.scan(imageFile);
            String ret = scanner.getText().toString();
            System.out.println(ret);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (OcrException e) {
            throw new RuntimeException(e);
        } finally {
            if (tfile.exists()) {
                tfile.delete();
            }
        }
    }

    private static void ocr2() throws IOException, OcrException {
        // 指定要扫描的图像文件的路径
        String imageFile = "G:\\测试\\cs1.png";

        // 指定依赖文件的路径
        URL resourceURL = ClassLoader.getSystemResource("dependencies");
        String dependencies = resourceURL.getPath();
        // 创建一个 OcrScanner 对象
        OcrScanner scanner = new OcrScanner();
        // 设置 OcrScanner 对象的依赖文件路径
        scanner.setDependencies(dependencies);
        // 使用 OcrScanner 对象扫描指定的图像文件
        scanner.scan(imageFile);
        // 获取扫描的文本内容
        String scannedText = scanner.getText().toString();
        System.out.println(scannedText);
    }

}
