package com.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.music.entity.SongList;

import java.util.List;

public interface SongListService extends IService<SongList> {
    List<SongList> getRecommend();

    SongList getSongList(Integer id);

    IPage<SongList> search(String keyword, Integer pageSize, Integer pageNum);

    void delete(Integer id);

    void updateSongList(SongList songList);
}
