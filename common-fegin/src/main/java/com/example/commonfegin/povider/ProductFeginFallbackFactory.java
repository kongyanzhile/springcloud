package com.example.commonfegin.povider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * ProductFeginFallBack<br>
 * <p>
 * 作成日：2026/8/19<br>
 * 作成者：秦振兴<br>
 */
@Slf4j
@Component
public class ProductFeginFallbackFactory implements FallbackFactory<ProductFegin> {

    @Override
    public ProductFegin create(Throwable cause) {
        return new ProductFegin() {
            @Override
            public String getProduct(Integer id) {
                log.error("ProductFeginFallbackFactory 降级启动 {}, 商品id:【{}】", cause, id);
                return "降级方法启动";
            }
        };
    }
}
