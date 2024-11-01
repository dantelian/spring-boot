package com.example.springbootcommon.serviceImpl;

import com.example.springbootcommon.common.util.ImageUtil;
import com.example.springbootcommon.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public void compressImage(MultipartFile file, HttpServletResponse response) throws IOException {
        if (!ImageUtil.isImage(file)) {
            throw new RuntimeException("文件需为图片");
        }
        int targetSizeKB = 400; // 目标压缩大小（KB）

        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        BufferedImage compressedImage = ImageUtil.compressImage(bufferedImage, targetSizeKB);

        // 保存到本地
//        String outputImagePath = "G:\\测试1.jpg"; // 输出图片路径
//        ImageIO.write(compressedImage, "jpg", new File(outputImagePath));

        // 设置内容类型和头信息
        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "inline; filename=\"image.jpg\"");

        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(response.getOutputStream());
            ImageIO.write(compressedImage, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
