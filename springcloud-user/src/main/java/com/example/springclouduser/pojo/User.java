package com.example.springclouduser.pojo;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author Caolele
 * @since 2023-06-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private String username;

    private  String password;
}
