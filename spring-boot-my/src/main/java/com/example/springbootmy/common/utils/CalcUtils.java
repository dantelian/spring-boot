package com.example.springbootmy.common.utils;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 公式计算
 */
public class CalcUtils {

    /**
     * 公式计算
     * @param formula 计算公式
     * @param paramMap 公式中需要替换的参数
     * @return
     */
    public static BigDecimal calc(String formula, Map<String, BigDecimal> paramMap) {
        JexlContext jc = new MapContext();
        paramMap.forEach((key, val) -> {
            jc.set(key, val);
        });
        // 替换%
        String formulaTmp = formula.replaceAll("%", "/100");
        Expression e = new JexlEngine().createExpression(formulaTmp);
        Object result = e.evaluate(jc);
        return new BigDecimal(String.valueOf(result));
    }

    public static void main(String[] args) {
//        String formula = "(x + 1) * 1.1"; // 公式有小数，可能出现精度不准问题
        String formula = "(x + 1) * y"; // 改为参数则不会有精度问题

        Map<String, BigDecimal> decimalMap = new HashMap<>();
        decimalMap.put("x", new BigDecimal("1.43"));
        decimalMap.put("y", new BigDecimal("1.1"));
        BigDecimal r = calc(formula, decimalMap);
        System.out.println(r);
    }

}
