package com.example.springcloudconsumer.contoller;

import com.example.commonutil.config.UserContext;
import com.example.springcloudconsumer.service.WareImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ware<br>
 * <p>
 * 作成日：2026/8/19<br>
 * 作成者：秦振兴<br>
 */
@RestController
@RequestMapping("/ware")
public class WarehouseController {

    @Resource
    private WareImpl ware;

    @GetMapping("/get")
    public String wareProduct(){
        String userId = UserContext.getUserId();
        System.out.println("ware获取到的用户id" + userId);
        return ware.getWare();
    }
}
