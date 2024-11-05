package com.example.springbootcommon.common.easyExcel;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DropDown {
    String[] value();

}