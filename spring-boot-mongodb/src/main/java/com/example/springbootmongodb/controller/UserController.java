package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.model.entity.User;
import com.example.springbootmongodb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 新增或修改
    @PostMapping("/createUser")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // 修改
    @PostMapping("/editUser")
    public User editUser(@RequestBody User user) {
        return userService.editUser(user);
    }

    @GetMapping("/delete/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUserByName/{name}")
    public User getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @GetMapping("/getList")
    public List<User> getList(User user) {
        return userService.getList(user);
    }


}
