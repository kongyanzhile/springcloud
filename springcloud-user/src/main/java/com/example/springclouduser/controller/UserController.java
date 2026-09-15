package com.example.springclouduser.controller;

import com.example.springclouduser.pojo.UserVo;
import com.example.springclouduser.service.UserServiceImpl;
import com.example.springclouduser.util.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * UserController<br>
 * <p>
 * 作成日：2026/8/25<br>
 * 作成者：秦振兴<br>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserServiceImpl userService;

    @PostMapping("/login")
    public Map<String, String> login() {

        return userService.login("qin", "123fdsfsfdsf");
    }

}
