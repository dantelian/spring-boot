package com.example.springbootmybatis.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.springbootmybatis.entity.User;
import com.example.springbootmybatis.mapper.UserMapper;
import com.example.springbootmybatis.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public String getUserBySex(int sex) {
        Wrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("sex", 2);
        List<User> list = this.baseMapper.selectList(wrapper);

        String name = "";
        for (User user : list) {
            name += user.getName() + "!";
        }
        return name;
    }

    @Override
    public List<User> selectByWrapper() {
        Wrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("sex", 2);
        List<User> list = this.baseMapper.selectList(wrapper);

        for (User user: list) {
            System.out.println(user.getName());
        }
        return list;
    }

    @Override
    public void getPageByWrapper() {
        Wrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("sex", 1);

        Page<User> page = new Page<>(1,5);
        List<User> list = this.baseMapper.selectPage(page, wrapper);

        System.out.println(list.size());
    }

    @Override
    public Page<User> getPageList() {
        Page<User> page = new Page<>(1,5);
        page.setRecords(this.baseMapper.getPageList(page));
        return page;
    }

    @Override
    public Page getPageMap() {
        Page page = new Page<>(1,5);
        List<Map<String,Object>> list = this.baseMapper.getPageMap(page);

        page.setRecords(list);
        return page;
    }

}
