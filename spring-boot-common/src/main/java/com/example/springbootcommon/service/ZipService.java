package com.example.springbootcommon.service;

import java.io.IOException;
import java.util.List;

public interface ZipService {

    List<String> unzip(String sourceFile, String targetFolder) throws IOException;

    // 文件操作
    void fileOperation() throws IOException;

}
