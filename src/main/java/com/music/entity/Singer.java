package com.music.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Singer implements Serializable {
    private Integer id;
    private String name;
    private String imgSrc;
}
