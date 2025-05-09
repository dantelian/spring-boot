package com.example.springbootmy;


import cn.hutool.core.lang.Dict;
import cn.hutool.extra.expression.ExpressionUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class SpringBootMyApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void hutoolExpr() {
		Dict dict = Dict.create()
				.set("aa", 100)
				.set("bb", 45)
				.set("cc", -199);

		// 简单的数学表达式
		String exprStr = "3 + 5 * 2";
		Object result = ExpressionUtil.eval(exprStr, dict);
//		double result = Expr.evalDouble(exprStr);
		System.out.println("表达式 " + exprStr + " 的计算结果是: " + result);

		// 带有变量的表达式
		String varExprStr = "(1.43 + 1) * 1.1";
//		// 设置变量值
//		Expr.setVar("a", 10);
//		Expr.setVar("b", 2);
//		Expr.setVar("c", 3);
//		double varResult = Expr.evalDouble(varExprStr);
		Object varResult = ExpressionUtil.eval(varExprStr, new HashMap<>());
		System.out.println("带有变量的表达式 " + varExprStr + " 的计算结果是: " + varResult);
	}

}
