package com.example.springbootcommon.serviceImpl;

import com.example.springbootcommon.common.util.ZipUtil;
import com.example.springbootcommon.service.ZipService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class ZipServiceImpl implements ZipService {

    @Override
    public List<String> unzip(String sourceFile, String targetFolder) throws IOException {
        return ZipUtil.fileUncompressing(sourceFile, targetFolder);
    }

    @Override
    public void fileOperation() throws IOException {
        // 获取线上文件
        String uri = "";
        URL url = new URL(uri);
        InputStream inputStream = url.openStream();

        // 保存到本地
        OutputStream os = null;
        try {
            os = new FileOutputStream("G:\\测试\\测试.jpg");

            // 读取输入流数据并写入到输出流中
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            inputStream.close();
            os.close();
        }

        // 获取本地文件
        String path = "G:\\测试\\测试.jpg";
        FileInputStream fileInputStream = new FileInputStream(path);
        String filename = path.substring(path.lastIndexOf("\\") + 1);
        MultipartFile multipartFile = null;
        try {
            multipartFile = new MockMultipartFile(filename, filename, ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建临时文件夹
        Path tempFolder = Files.createTempDirectory("tempFolder");
        String targetFolder = tempFolder.toFile().getAbsolutePath();
        log.info("临时文件夹：{}", targetFolder);

        // 删除临时目录
        // 方式一
        ZipUtil.deleteFolder(tempFolder);
        // 方式二
//        Path dir = Paths.get("path/to/directory");
//        ZipUtil.deleteFolder(dir);
        // 方式三
//        Files.delete(dir); // 删除文件夹（不能为非空目录）

    }

}
