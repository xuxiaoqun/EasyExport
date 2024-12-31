package com.example.easyexport;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.easyexport.anotation.EasyExport;
import com.example.easyexport.mapper.UserMapper;
import com.example.easyexport.model.PageInfo;
import com.example.easyexport.model.TestUserInfo;
import com.example.easyexport.model.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/1")
    @EasyExport(pageQuery = false, fileName = "test", clazz = TestUserInfo.class)
    public List<TestUserInfo> getUserInfo(HttpServletResponse response){
        return userMapper.selectList(null).stream().map(e -> new TestUserInfo(e.getId().toString(),e.getUserName(),e.getEmail())).toList();
    }


    //文件导出，分批查询写入，避免数据量过大，内存溢出
    @RequestMapping("/3")
    @EasyExport(fileName = "test1", clazz = TestUserInfo.class)
    public List<TestUserInfo> getUserInfo(HttpServletResponse response, @RequestBody PageInfo pageInfo){
        IPage<UserEntity> page = new Page<>(pageInfo.getPage(),pageInfo.getSize());
        return userMapper.selectPage(page,null).getRecords().stream().map(e -> new TestUserInfo(e.getId().toString(),e.getUserName(),e.getEmail())).toList();
    }

    //普通分页查询
    @RequestMapping("/2")
    public List<TestUserInfo> getUserInfo2(@RequestBody PageInfo pageInfo){
        IPage<UserEntity> page = new Page<>(pageInfo.getPage(),pageInfo.getSize());
        return userMapper.selectPage(page,null).getRecords().stream().map(e -> new TestUserInfo(e.getId().toString(),e.getUserName(),e.getEmail())).toList();
    }

}
