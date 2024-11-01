package com.example.springbootcommon.service;

import javax.servlet.http.HttpServletResponse;

public interface EasyPoiWordService {

    /**
     * easy poi 根据模板生成wold
     * @param response
     */
    void buildEasyPoiTemplateWorld(HttpServletResponse response);

}
