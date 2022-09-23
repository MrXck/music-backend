package com.music.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SongListComment implements Serializable {
    private Integer id;
    private String content;
    private Integer userId;
    private Integer songListId;
    private Integer star;
    private LocalDateTime createTime;

    @TableField(exist = false)
    private User user;
}
