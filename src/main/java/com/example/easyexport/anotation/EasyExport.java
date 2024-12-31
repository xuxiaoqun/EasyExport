package com.example.easyexport.anotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyExport {

    boolean pageQuery() default true;

    String fileName();

    Class<?> clazz();
}
