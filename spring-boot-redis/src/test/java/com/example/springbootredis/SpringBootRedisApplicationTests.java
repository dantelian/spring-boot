package com.example.springbootredis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootredis.common.util.RedisUtil;
import com.example.springbootredis.model.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

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

	@Test
	public void tryJson() {
		Order order = new Order();
		order.setId("11");
		order.setNum(1);
		order.setDay(LocalDateTime.now());

		RedisUtil.StringOps.set("order", JSON.toJSONString(order));
	}

	@Test
	public void tryJson1() {
		String str = RedisUtil.StringOps.get("order");
		Order order = JSONObject.parseObject(str, Order.class);
		System.out.println(order.getDay());
	}

}
