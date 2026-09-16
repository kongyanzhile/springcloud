package com.example.springcloudsearch.respository;

import com.example.common.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * ProductRespository<br>
 * <p>
 * 作成日：2026/9/16<br>
 * 作成者：秦振兴<br>
 */
@Component
public interface ProductRespository extends ElasticsearchRepository<Product, Long> {
    Page<Product> findByNameLikeAndPriceBetween(String keyword, BigDecimal min, BigDecimal max, Pageable pageable);

}
