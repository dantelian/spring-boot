package com.example.springbootcommon.serviceImpl;

import com.example.springbootcommon.common.util.ZipUtil;
import com.example.springbootcommon.service.ZipService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class ZipServiceImpl implements ZipService {

    @Override
    public List<String> unzip(String sourceFile, String targetFolder) throws IOException {
        List<String> pathList;
        if (StringUtils.isEmpty(targetFolder)) {
            // 创建临时文件夹
            Path tempFolder = Files.createTempDirectory("tempFolder");

            targetFolder = tempFolder.toFile().getAbsolutePath();
            log.info("临时文件夹：{}", targetFolder);

            pathList = ZipUtil.fileUncompressing(sourceFile, targetFolder);

            // 删除临时目录
            ZipUtil.deleteFolder(tempFolder);

//            Path dir = Paths.get("path/to/directory");
//            ZipUtil.deleteFolder(dir);

//            Files.delete(dir); // 删除文件夹（不能为非空目录）
        } else {
            pathList = ZipUtil.fileUncompressing(sourceFile, targetFolder);
        }

        return pathList;
    }

}
