package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.entity.SongList;
import com.music.entity.SongListCollect;

import java.util.List;

public interface SongListCollectService extends IService<SongListCollect> {
    boolean addSongListCollect(Integer id);

    boolean notSongListCollect(Integer id);

    List<SongList> getSongListCollect();
}
