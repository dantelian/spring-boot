package com.example.springbootsecurityjwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getUsername());
        return "需要登录的私有接口";
    }

    @GetMapping("/admin/role")
    public ResponseEntity adminRoleApi() {
        return ResponseEntity.ok("需要角色的私有接口");
    }

}
