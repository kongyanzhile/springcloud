package com.example.springcloudprovider.controller;

import com.example.common.feigin.config.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Product<br>
 * <p>
 * 作成日：2026/8/19<br>
 * 作成者：秦振兴<br>
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/get/{id}")
    public String getProduct(@PathVariable Integer id, HttpServletRequest request){
        System.out.println("product获取到的用户id" + UserContext.getUserId());
        return "商品";
    }
}
