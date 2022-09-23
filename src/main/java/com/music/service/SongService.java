package com.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.music.entity.Song;

import java.util.List;

public interface SongService extends IService<Song> {
    List<Song> getNewSong();

    IPage<Song> search(String keyword, Integer pageSize, Integer pageNum);
}
