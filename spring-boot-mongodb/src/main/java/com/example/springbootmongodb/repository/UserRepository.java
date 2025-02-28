package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    /**
     * 根据 name 查询
     * where name = ?
     */
    User findByName(String name);

    /**
     * 根据 name 模糊查询
     * where name like ?
     */
    List<User> findByNameLike(String name);

    /**
     * 多条件查询
     * 忽略大小写    $options: 'i'
     */
//    @Query("{ 'name' : ?0, 'age' : { $gt: ?1 } }") // where name = ? and age > ?
    @Query("{ 'name' : { $regex: ?0, $options: 'i' }, 'age' : { $gt: ?1 } }") // where name like ? and age > ?
    List<User> findUsersByNameAndAgeGreaterThan(String name, int age);

}
