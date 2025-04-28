package com.example.springbootcommon.service;

import java.io.IOException;
import java.util.List;

public interface ZipService {

    List<String> unzip(String sourceFile, String targetFolder) throws IOException;

    void fileOperation() throws IOException;

}
