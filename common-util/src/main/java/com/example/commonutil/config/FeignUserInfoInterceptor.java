package com.example.commonutil.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

/**
 * FeignUserInfoInterceptor<br>
 * <p>
 * 作成日：2026/9/11<br>
 * 作成者：秦振兴<br>
 */
@Configuration
public class FeignUserInfoInterceptor implements RequestInterceptor {

    private static final List<String> HEADERS_TO_PROPAGATE = List.of("X-User-Id");

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            for (String headerName : HEADERS_TO_PROPAGATE) {
                String value = request.getHeader(headerName);
                if (value != null) {
                    template.header(headerName, value);
                }
            }
        }
    }
}
