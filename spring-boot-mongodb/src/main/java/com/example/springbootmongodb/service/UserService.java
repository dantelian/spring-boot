package com.example.springbootmongodb.service;

import com.example.springbootmongodb.model.entity.User;
import com.example.springbootmongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public User saveUser(User user) {
        // 方式一：
        return userRepository.save(user); // 可新增和修改
        // 方式二：
//        return userRepository.insert(user);
    }

    public User editUser(User user) {
        // 方式一：
//        // 查找要更新的数据
//        User data = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("data not found"));
//        // 更新字段
//        data.setAge(user.getAge());
//        // 保存更新后的数据
//        return userRepository.save(data);

        // 方式二：
        Query query = new Query(Criteria.where("_id").is(user.getId()));
        Update update = new Update().set("age", user.getAge());
        mongoTemplate.updateFirst(query, update, User.class);
        return null;
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> getList(User user) {
//        return userRepository.findByNameLike(user.getName());
        return userRepository.findUsersByNameAndAgeGreaterThan(user.getName(), user.getAge());

//        return null;
    }





}
