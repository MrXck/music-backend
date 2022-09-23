package com.music.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Song implements Serializable {
    private Integer id;
    private String name;
    private String imgSrc;
    private String src;
    private LocalDateTime createTime;
    private Integer time;
    private String lyric;

    @TableField(exist = false)
    private List<Singer> singers;

    @TableField(exist = false)
    private Boolean isLove;
}
