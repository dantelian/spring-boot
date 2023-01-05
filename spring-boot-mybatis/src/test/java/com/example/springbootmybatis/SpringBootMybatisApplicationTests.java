package com.example.springbootmybatis;

import com.example.springbootmybatis.entity.User;
import com.example.springbootmybatis.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisApplicationTests {
	@Autowired
	private IUserService userService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
//		System.out.println(userService.getUserBySex(1));

//		User user = new User();
//		user.setName("1234");
//		System.out.println(userService.insert(user));

//		user.setId("9205e553692349a9ba4c6bd53dbd3f41");
//		System.out.println(userService.updateById(user));

//		System.out.println(userService.deleteById("9205e553692349a9ba4c6bd53dbd3f41"));

//		System.out.println(userService.selectByWrapper().size());
//		userService.getPageByWrapper();
//		System.out.println(userService.getPageList());
		userService.getPageMap();
	}



}
