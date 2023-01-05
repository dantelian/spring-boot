package com.example.springbootredis;

import com.example.springbootredis.common.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedisApplicationTests {

	@Test
	public void contextLoads() {
//		RedisUtil.StringOps.set("a", "1");
//		if (RedisUtil.KeyOps.hasKey("a")) {
//			RedisUtil.StringOps.append("a", "-a");
//		}

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			RedisUtil.KeyOps.expireAt("a", sf.parse("2021-07-01 10:57:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
