package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.entity.Singer;
import com.music.entity.SingerSong;
import com.music.entity.Song;
import com.music.entity.SongCollect;
import com.music.mapper.SingerMapper;
import com.music.mapper.SingerSongMapper;
import com.music.mapper.SongCollectMapper;
import com.music.mapper.SongMapper;
import com.music.service.SongCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongCollectServiceImpl extends ServiceImpl<SongCollectMapper, SongCollect> implements SongCollectService {

    @Autowired
    private SongCollectMapper songCollectMapper;

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SingerMapper singerMapper;

    @Autowired
    private SingerSongMapper singerSongMapper;

    @Override
    public List<Song> getByUserId(Integer userId) {
        LambdaQueryWrapper<SongCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SongCollect::getUserId, userId);
        List<SongCollect> songCollects = songCollectMapper.selectList(queryWrapper);
        List<Integer> songIds = songCollects.stream().map(SongCollect::getSongId).collect(Collectors.toList());
        List<Song> songs = new ArrayList<>();
        LambdaQueryWrapper<Song> songLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(songIds.size() > 0){
            songLambdaQueryWrapper.in(Song::getId, songIds);
            songs = songMapper.selectList(songLambdaQueryWrapper);

            songs = songs.stream().map(item -> {

                LambdaQueryWrapper<SingerSong> singerSongLambdaQueryWrapper = new LambdaQueryWrapper<>();
                singerSongLambdaQueryWrapper.eq(SingerSong::getSongId, item.getId());
                List<SingerSong> singerSongs = singerSongMapper.selectList(singerSongLambdaQueryWrapper);

                List<Integer> singerIds = singerSongs.stream().map(SingerSong::getSingerId).collect(Collectors.toList());

                LambdaQueryWrapper<Singer> singerLambdaQueryWrapper = new LambdaQueryWrapper<>();
                singerLambdaQueryWrapper.in(Singer::getId, singerIds);
                List<Singer> singers = singerMapper.selectList(singerLambdaQueryWrapper);
                item.setSingers(singers);
                item.setIsLove(true);
                return item;
            }).collect(Collectors.toList());
        }
        return songs;
    }

    @Override
    public void removeLoveMusic(Integer songId, Integer userId) {
        LambdaQueryWrapper<SongCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SongCollect::getSongId, songId);
        queryWrapper.eq(SongCollect::getUserId, userId);
        SongCollect songCollect = songCollectMapper.selectOne(queryWrapper);
        songCollectMapper.deleteById(songCollect.getId());
    }

    @Override
    public boolean addLoveMusic(Integer songId, Integer userId) {
        LambdaQueryWrapper<SongCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SongCollect::getSongId, songId);
        queryWrapper.eq(SongCollect::getUserId, userId);
        Integer count = songCollectMapper.selectCount(queryWrapper);
        if(count != 0){
            return false;
        }
        SongCollect songCollect = new SongCollect();
        songCollect.setSongId(songId);
        songCollect.setUserId(userId);
        songCollectMapper.insert(songCollect);
        return true;
    }
}
