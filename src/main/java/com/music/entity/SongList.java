package com.music.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SongList implements Serializable {
    private Integer id;
    private String name;
    private String imgSrc;
    private Integer creator;
    private LocalDateTime createTime;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private List<Song> songs;

    @TableField(exist = false)
    private Boolean isCollect;

    @TableField(exist = false)
    private Integer songCount;

}
