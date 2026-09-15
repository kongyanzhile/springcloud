package com.example.springcloudprovider.service;


import org.springframework.stereotype.Service;

/**
 * UserImpl<br>
 * <p>
 * 作成日：2026/8/20<br>
 * 作成者：秦振兴<br>
 */
@Service
public class UserImpl implements User{

    @Override
    public String getUser(Integer id) {
        return "返回用户" + id;
    }
}
