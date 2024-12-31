package com.example.easyexport.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestUserInfo {

    @ExcelProperty("id")
    private String id;
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("邮箱")
    private String email;
}
