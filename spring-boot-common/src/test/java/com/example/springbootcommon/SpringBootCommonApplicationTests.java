package com.example.springbootcommon;

import com.example.springbootcommon.service.OfficeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootCommonApplicationTests {
	@Autowired
	private OfficeService officeService;

	@Test
	public void contextLoads() {
	}

	/**
	 * apache.poi 生成wold文档
	 */
	@Test
	public void createApachePoiWorld() {
		officeService.buildApacheWord(null);
	}

	/**
	 * poi-tl 根据模板生成wold
	 */
	@Test
	public void createPoiTlTemplateWorld() {
		officeService.createPoiTlTemplateWorld();
	}

}
