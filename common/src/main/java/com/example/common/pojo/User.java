package com.example.common.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;
import java.util.List;

@Data
@Document(indexName = "users")  // 索引名称、
@Setting(shards = 3, replicas = 1) // 分片数、副本数
public class User {

    @Id  // 文档ID
    private String id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")  // 全文本字段，使用中文分词器
    private String username;

    @Field(type = FieldType.Keyword)  // 关键字字段，不分词
    private String email;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Date, format = DateFormat.date_time)  // 日期字段
    private Date createdAt;

    @Field(type = FieldType.Nested)  // 嵌套对象字段
    private List<Order> orders;

    @Data
    public static class Order {
        private String orderId;
        private Double amount;
        private Date orderDate;
    }
}
