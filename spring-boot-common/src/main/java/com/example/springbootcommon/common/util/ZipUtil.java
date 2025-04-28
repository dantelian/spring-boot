package com.example.springbootcommon.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

@Slf4j
public class ZipUtil {

    /**
     * 文件解压缩
     * @return
     */
    public static List<String> fileUncompressing(String zipFilePath, String targetFolder) {
        List<String> pathList=new ArrayList<>();

        try {
            // 创建目标文件夹
            File targetDir = new File(targetFolder);
            targetDir.mkdirs();

            // 打开压缩文件
//            ZipFile zipFile = new ZipFile(zipFilePath);
            ZipFile zipFile = new ZipFile(zipFilePath, Charset.forName("gbk"));

            // 遍历压缩文件中的所有条目
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                // 解压缩条目到目标文件夹
                String entryName = entry.getName();
                entryName = new String(entryName.getBytes(Charset.forName("gbk")));
                File entryFile = new File(targetDir, entryName);

                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    entryFile.getParentFile().mkdirs();
                    InputStream inputStream = zipFile.getInputStream(entry);
                    OutputStream outputStream = new FileOutputStream(entryFile);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                    outputStream.close();
                    inputStream.close();
                }

                // 拼接路径
                Path path = Paths.get(targetFolder, entryName);
                pathList.add(path.toString());
            }

            // 关闭压缩文件
            zipFile.close();

            log.info("文件读取完成");

        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathList;
    }

    /**
     * 删除指定目录及子目录
     * @param dir
     */
    public static void deleteFolder(Path dir) {
        try (Stream<Path> paths = Files.walk(dir)) {
            paths.sorted(Comparator.reverseOrder()) // 反向排序确保先删除子目录和文件再删除目录本身
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace(); // 处理删除过程中的异常
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace(); // 处理IO异常
        }
    }

}
