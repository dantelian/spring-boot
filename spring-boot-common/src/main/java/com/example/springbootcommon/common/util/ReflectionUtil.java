package com.example.springbootcommon.common.util;

import java.lang.reflect.Field;
import java.util.Objects;

public class ReflectionUtil {

    /**
     * 空属性判断-是否全为空
     * @param bean
     * @return
     */
    public static boolean areAllFieldsEmpty(Object bean) {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields(); // 获取所有字段，包括私有字段
        boolean allEmpty = true; // 假设所有字段都为空，除非发现至少一个非空字段
        for (Field field : fields) {
            field.setAccessible(true); // 设置私有字段可访问
            try {
                Object value = field.get(bean); // 获取字段值
                if (value != null && !Objects.toString(value).trim().isEmpty()) { // 检查值是否非空且不为空白字符串
                    allEmpty = false; // 发现至少一个非空字段，则返回false
                    break; // 可以提前退出循环，因为我们只需要知道是否存在至少一个非空字段即可。
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // 处理异常，例如私有字段无法访问的情况。
                return false; // 如果无法访问某个字段，则认为该对象不满足条件。
            }
        }
        return allEmpty; // 如果所有字段都为空或null，则返回true；否则返回false。
    }
}
