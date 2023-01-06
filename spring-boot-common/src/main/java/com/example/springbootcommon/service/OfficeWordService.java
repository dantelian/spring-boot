package com.example.springbootcommon.service;


import javax.servlet.http.HttpServletResponse;

/**
 * @program: spring-boot-common
 * @description:
 * @create: 2023-01-06 09:19
 **/
public interface OfficeWordService {

    /**
     * apache.poi 生成word文件
     */
    void buildApacheWord(HttpServletResponse response);

}
