package com.example.springcloudsearch.controller;

import com.example.common.pojo.Product;
import com.example.springcloudsearch.service.ProductServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * ProductController<br>
 * <p>
 * 作成日：2026/9/15<br>
 * 作成者：秦振兴<br>
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/search/{page}/{size}")
    public Page<Product> search(String keyword, @PathVariable int page, @PathVariable int size, BigDecimal minPrice, BigDecimal maxPrice) {
        return productService.search(keyword, page, size, minPrice, maxPrice);
    }

    @PostMapping("/save")
    public void save(){
        productService.save();
    }

    @DeleteMapping("/delete")
    public void delete(){
        productService.deleteAll();
    }
}
