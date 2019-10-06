package com.ichat.controller;

import com.ichat.mapper.UsersMapper;
import com.ichat.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private UsersMapper usersMapper;
    @GetMapping("/hello")
    public Users hello(){
        Users user = usersMapper.selectByPrimaryKey("123");
        return user;
    }
}
