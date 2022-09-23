package com.music.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SongCollect implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer songId;
}
