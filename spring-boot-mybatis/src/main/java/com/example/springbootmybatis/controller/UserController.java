package com.example.springbootmybatis.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.springbootmybatis.entity.User;
import com.example.springbootmybatis.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private IUserService userService;

    @ResponseBody
    @RequestMapping(value = "getUserById")
    public String getUserById(String id) {
        User user = userService.selectById(id);
        return user.toString();
    }

    @ResponseBody
    @RequestMapping(value = "getUserBySex")
    public String getUserBySex(int sex) {
        return userService.getUserBySex(sex);
    }

    @ResponseBody
    @RequestMapping(value = "getPageMap")
    public Page getPageMap() {
        return userService.getPageMap();
    }

}
