package com.music.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String avatar;

    private String phone;

    private LocalDateTime createTime;
}
