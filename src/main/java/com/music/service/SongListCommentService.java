package com.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.music.entity.SongListComment;

import java.util.List;

public interface SongListCommentService extends IService<SongListComment> {
    IPage<SongListComment> getBySongListId(Integer songListId, Integer pageSize, Integer pageNum);

    List<SongListComment> getWonderfulSongListCommentById(Integer songListId);

    boolean star(Integer songListCommentId);

    boolean addComment(SongListComment songListComment, Integer userId);
}
