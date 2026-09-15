package com.example.springclouduser.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserVo<br>
 * <p>
 * 作成日：2026/8/25<br>
 * 作成者：秦振兴<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private Integer userId;

    private String name;
}
