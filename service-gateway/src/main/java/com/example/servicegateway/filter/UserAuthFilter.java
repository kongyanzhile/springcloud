package com.example.servicegateway.filter;

import com.example.servicegateway.properties.AuthUrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

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

        String path = exchange.getRequest().getURI().getPath();

        // 不需要权限的
        List<String> noauthurlList = authUrlProperties.getNoauthurl();
        for (String noauthurl : noauthurlList) {
            if (antPathMatcher.match(noauthurl, path)) {
                String token = exchange.getRequest().getHeaders().getFirst("Authorization");
                if (StringUtils.hasText(token)){ // 如果在登录的情况下访问了不需要登录的url，把token也发送过去
                    String[] split = token.split(" ");
                    if (split.length == 2 && "Bearer".equalsIgnoreCase(split[0])) {
                        String userId = redisTemplate.opsForValue().get("login:token:" + split[1]);
                        if (StringUtils.hasText(userId)) {
                            exchange = exchange.mutate()
                                    .request(builder -> builder.header("X-User-Id", userId))
                                    .build();
                        }
                    }
                }
                return chain.filter(exchange);
            }
        }

        // 需要权限的
        List<String> authurlList = authUrlProperties.getAuthurl();
        for (String authurl : authurlList) {
            if (antPathMatcher.match(authurl, path)) {
                String token = exchange.getRequest().getHeaders().getFirst("Authorization");
                if (StringUtils.hasText(token)){
                    String[] split = token.split(" ");
                    if (split.length != 2 || !"Bearer".equalsIgnoreCase(split[0])) {
                        return this.unauthorized(exchange);
                    }
                    String userId = redisTemplate.opsForValue().get("login:token:" + split[1]);
                    if (StringUtils.hasText(userId)) {
                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(builder -> builder.header("X-User-Id", userId))
                                .build();
                        return chain.filter(mutatedExchange);
                    } else {
                        return this.unauthorized(exchange);
                    }
                } else {
                    return this.unauthorized(exchange);
                }
            }
        }
        return this.unauthorized(exchange);
    }

    /**
     * 返回无权限状态
     * @param exchange
     * @return
     */
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
