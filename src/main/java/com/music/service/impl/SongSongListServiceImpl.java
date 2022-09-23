package com.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.entity.SongSongList;
import com.music.mapper.SongSongListMapper;
import com.music.service.SongSongListService;
import org.springframework.stereotype.Service;

@Service
public class SongSongListServiceImpl extends ServiceImpl<SongSongListMapper, SongSongList> implements SongSongListService {
}
