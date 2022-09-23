package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.entity.Song;
import com.music.entity.SongCollect;

import java.util.List;

public interface SongCollectService extends IService<SongCollect> {
    List<Song> getByUserId(Integer userId);

    void removeLoveMusic(Integer songId, Integer userId);

    boolean addLoveMusic(Integer songId, Integer userId);
}
