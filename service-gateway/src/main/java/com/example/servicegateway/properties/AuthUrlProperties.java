package com.example.servicegateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.auth")
@Data
public class AuthUrlProperties {
    /**
     * 放行路径
     */
    private List<String> noauthurl ;

    /**
     * 验证登录路径
     */

    private List<String> authurl ;

    /**
     * 登录地址
     */
    private String loginPageUrl ;
}
