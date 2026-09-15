package com.example.springclouduser.service;

import com.example.springclouduser.pojo.SecurityConst;
import com.example.springclouduser.pojo.User;
import com.example.springclouduser.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * UserServiceImpl<br>
 * <p>
 * 作成日：2026/9/10<br>
 * 作成者：秦振兴<br>
 */
@Service
public class UserServiceImpl {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Map<String, String> login(String username, String password) {

        User user = new User(123l, username, password);
        try {
//            user = authenticate(username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //创建token
        Map<String, Object> claims = getClaims(user);
        String token = JwtUtil.createJWT(username, claims);
        redisTemplate.opsForValue().set("login:token:" + token, String.valueOf(user.getId()));
        return Map.of(SecurityConst.TOKEN, token);
    }

    private Map<String, Object> getClaims(User user) {
        return Map.of(SecurityConst.USER_ID, user.getId().toString(), SecurityConst.USERNAME, user.getUsername());
    }
}
