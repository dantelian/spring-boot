package com.example.springbootcommon.controller;

import com.example.springbootcommon.service.EasyPoiWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * easy-poi word
 */
@RestController
@RequestMapping("/easyPoiWord")
public class EasyPoiWordController {

    @Autowired
    private EasyPoiWordService easyPoiWordService;

    /**
     * easy-poi export word
     * @param response
     */
    @GetMapping("/exportWordTemplate")
    public void exportWordTemplate(HttpServletResponse response) {
        easyPoiWordService.buildEasyPoiTemplateWorld(response);
    }


}
