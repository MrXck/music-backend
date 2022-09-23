package com.music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.entity.SingerSong;
import com.music.mapper.SingerSongMapper;
import com.music.service.SingerSongService;
import org.springframework.stereotype.Service;

@Service
public class SingerSongServiceImpl extends ServiceImpl<SingerSongMapper, SingerSong> implements SingerSongService{
}
