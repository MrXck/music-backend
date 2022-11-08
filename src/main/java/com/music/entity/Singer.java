package com.music.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Singer implements Serializable {
    private Integer id;
    private String name;
    private String imgSrc;

    @TableField(exist = false)
    private List<Song> songs;

    @TableField(exist = false)
    private List<Integer> songIds;
}
