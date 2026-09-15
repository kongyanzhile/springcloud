package com.example.springcloudprovider.controller;

import com.example.springcloudprovider.service.User;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User<br>
 * <p>
 * 作成日：2026/8/20<br>
 * 作成者：秦振兴<br>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private User user;

    @GetMapping("/get-user/{id}")
    public String getUser(@PathVariable Integer id){

        return user.getUser(id);
    }
}
