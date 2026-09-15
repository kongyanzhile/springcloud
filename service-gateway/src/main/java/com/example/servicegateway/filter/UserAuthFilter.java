package com.example.servicegateway.filter;

import com.example.servicegateway.properties.AuthUrlProperties;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;

/**
 * UserAuthFilter<br>
 * <p>
 * 作成日：2026/9/10<br>
 * 作成者：秦振兴<br>
 */
@Component
public class UserAuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private AuthUrlProperties authUrlProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    //定义路径匹配器（线程安全的）
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 不需要权限的
        List<String> noauthurlList = authUrlProperties.getNoauthurl();
        for (String noauthurl : noauthurlList) {
            String path = exchange.getRequest().getURI().getPath();
            if (antPathMatcher.match(noauthurl, path)) {
                return chain.filter(exchange);
            }
        }

        // 需要权限的
        List<String> authurlList = authUrlProperties.getAuthurl();
        for (String authurl : authurlList) {
            String path = exchange.getRequest().getURI().getPath();
            if (antPathMatcher.match(authurl, path)) {
                String token = exchange.getRequest().getHeaders().getFirst("Authorization");
                String[] split = token.split(" ");
                String userId = redisTemplate.opsForValue().get("login:token:" + split[1]);
                if (!Objects.isNull(userId)) {
                    System.out.println("[网关] 即将设置 X-User-Id = " + userId);
                    ServerWebExchange mutatedExchange = exchange.mutate()
                            .request(builder -> builder.header("X-User-Id", userId))
                            .build();
                    System.out.println("[网关-转发前验证] X-User-Id = "
                            + mutatedExchange.getRequest().getHeaders().getFirst("X-User-Id"));

                    return chain.filter(mutatedExchange);
                }
            }
        }
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
