package com.example.springcloudconsumer.service;

import com.example.commonfegin.povider.ProductFegin;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * WareImpl<br>
 * <p>
 * 作成日：2026/8/19<br>
 * 作成者：秦振兴<br>
 */
@Service
public class WareImpl {

    @Resource
    private ProductFegin productFegin;

    public String getWare() {
        String product = productFegin.getProduct(1);
        return product + ":45";
    }

}
