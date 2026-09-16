package com.example.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.util.Date;



/**
 * Product<br>
 * <p>
 * 作成日：2026/9/16<br>
 * 作成者：秦振兴<br>
 */
@Document(indexName = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setting(shards = 3, replicas = 1) // 分片数、副本数
public class Product {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String name;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String description;

    @Field(type = FieldType.Scaled_Float, scalingFactor = 100)
    private BigDecimal price;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Integer)
    private Integer stock;

    @Field(type = FieldType.Date)
    private Date createTime;
}


