package com.example.easyexport.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`users`")
public class UserEntity {
    private Long id;
    @TableField("username")
    private String userName;
    private String email;
}
