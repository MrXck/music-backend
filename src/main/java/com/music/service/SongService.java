package com.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.music.entity.Song;
import com.music.exception.SaveSongException;

import java.util.List;

public interface SongService extends IService<Song> {
    List<Song> getNewSong();

    IPage<Song> search(String keyword, Integer pageSize, Integer pageNum);

    Song getSongById(Integer id);

    void insertOrUpdate(Song song) throws SaveSongException;

    void deleteSong(Integer id);
}
