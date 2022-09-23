package com.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.music.entity.SongComment;


public interface SongCommentService extends IService<SongComment> {
    IPage<SongComment> getComment(Integer songId, Integer pageSize, Integer pageNum);

    boolean star(Integer songCommentId);
}
