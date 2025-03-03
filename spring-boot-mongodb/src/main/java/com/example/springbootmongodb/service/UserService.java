package com.example.springbootmongodb.service;

import com.example.springbootmongodb.model.entity.User;
import com.example.springbootmongodb.repository.UserRepository;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public void editUser(User user) {
        // 方式一：
//        // 查找要更新的数据
//        User data = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("data not found"));
//        // 更新字段
//        data.setAge(user.getAge());
//        // 保存更新后的数据
//        userRepository.save(data);

        // 方式二：
        Query query = new Query(Criteria.where("_id").is(user.getId()));
        Update update = new Update().set("age", user.getAge());
        mongoTemplate.updateFirst(query, update, User.class); // 修改第一条数据

        // 方式三：
//        Update update1 = new Update().set("gender", "1");
//        mongoTemplate.updateMulti(new Query(), update1, User.class);

        // 方式四：
//        Query query1 = Query.query(Criteria.where("name").is("anna"));
//        Update update2 = new Update().set("age", user.getAge()).set("city", "hongkong");
//        mongoTemplate.updateMulti(new Query(), update2, User.class);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);

        // 删除全部数据
//        mongoTemplate.remove(new Query(), User.class);

        // 条件删除
//        Query query = Query.query(Criteria.where("age").is(23));
//        DeleteResult deleteResult = mongoTemplate.remove(query, User.class); // mongoTemplate.remove(query, "t_user");
//        System.out.println("删除数量：" + deleteResult.getDeletedCount());
    }

    public List<User> getAllUsers() {
//        return userRepository.findAll();

        // 排序
        Sort sort = Sort.by(Sort.Direction.ASC, "age");
        return userRepository.findAll(sort);
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> getList(User user) {
//        return userRepository.findByNameLike(user.getName());
        return userRepository.findUsersByNameAndAgeGreaterThan(user.getName(), user.getAge());
    }

    public Object getPage(Integer current, Integer size, User user) {
        // 方式一：
//        Sort sort = Sort.by(Sort.Direction.ASC, "age");
//        int skip = (current - 1) * size;
//        Query query = new Query();
//        query.with(sort).skip(skip).limit(size);
//        return mongoTemplate.find(query, User.class);

        // 方式二：
        Sort sort = Sort.by(Sort.Direction.ASC, "age");
        PageRequest pageRequest = PageRequest.of(current - 1, size); // current 0是第一页 1是第二页
        Query query = new Query();
        query.with(pageRequest).with(sort);
        return mongoTemplate.find(query, User.class);
    }






}
