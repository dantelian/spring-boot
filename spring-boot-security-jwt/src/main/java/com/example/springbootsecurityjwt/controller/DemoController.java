package com.example.springbootsecurityjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/public")
    public String publicApi() {
        return "无需认证的公开接口";
    }

    @GetMapping("/private")
    public String privateApi() {
        return "需要登录的私有接口";
    }

    @GetMapping("/admin/role")
    public ResponseEntity adminRoleApi() {
        return ResponseEntity.ok("需要角色的私有接口");
    }

}
