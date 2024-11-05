package com.example.springbootcommon.common.easyExcel;

import java.lang.annotation.*;

/**
 * 下拉注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DropDown {
    String[] value();

}