package com.music.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SongSongList implements Serializable {
    private Integer id;
    private Integer songListId;
    private Integer songId;
}
