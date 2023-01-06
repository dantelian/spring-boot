package com.example.springbootcommon;

import com.example.springbootcommon.service.OfficeWordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootCommonApplicationTests {
	@Autowired
	private OfficeWordService officeWordService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void createApachePoiWorld() {
		officeWordService.buildApacheWord(null);
	}

}
