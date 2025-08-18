package com.example.springbootsecurityjwt.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.springbootsecurityjwt.config.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody JSONObject params) {
        String username = params.getString("username");
        String password = params.getString("password");
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("用户名或密码为空！");
        }
        // 执行校验

        String token = jwtUtils.generateToken(username);
        return ResponseEntity.ok(token);
    }

}