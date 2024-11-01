package com.example.springbootcommon.controller;

import com.example.springbootcommon.service.ApachePoiWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * apache-poi word
 */
@RestController
@RequestMapping("/apachePoiWord")
public class ApachePoiWordController {

    @Autowired
    private ApachePoiWordService apachePoiWordService;

    /**
     * apache-poi export word
     * @param response
     */
    @GetMapping("/exportWord")
    public void exportWord(HttpServletResponse response) {
        apachePoiWordService.buildApacheWord(response);
    }

    /**
     * apache-poi poi-tl export word
     * poi-tl 根据模板生成wold
     * @param response
     */
    @GetMapping("/exportTlTemplateWorld")
    public void exportTlTemplateWorld(HttpServletResponse response) {
        apachePoiWordService.buildPoiTlTemplateWorld(response);
    }

    /**
     * apache-poi word转pdf
     * 仅docx格式
     * @param file
     * @param response
     */
    @PostMapping("/word2pdf")
    public void word2pdf(@RequestPart("file") MultipartFile file, HttpServletResponse response) {
        apachePoiWordService.word2pdf(file, response);
    }


}
