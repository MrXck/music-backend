package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.entity.Singer;
import com.music.entity.SingerSong;
import com.music.entity.Song;
import com.music.entity.SongCollect;
import com.music.mapper.SingerMapper;
import com.music.mapper.SingerSongMapper;
import com.music.mapper.SongCollectMapper;
import com.music.mapper.SongMapper;
import com.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SingerMapper singerMapper;

    @Autowired
    private SongCollectMapper songCollectMapper;

    @Autowired
    private SingerSongMapper singerSongMapper;

    @Override
    public List<Song> getNewSong() {
        LambdaQueryWrapper<Song> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Song::getCreateTime);
        List<Song> list = songMapper.selectList(queryWrapper);
        if(list.size() > 10){
            list = list.subList(0, 10);
        }else {
            list = list.subList(0, list.size());
        }
        return list.stream().map(item -> {

            LambdaQueryWrapper<SingerSong> singerSongLambdaQueryWrapper = new LambdaQueryWrapper<>();
            singerSongLambdaQueryWrapper.eq(SingerSong::getSongId, item.getId());
            List<SingerSong> singerSongs = singerSongMapper.selectList(singerSongLambdaQueryWrapper);

            List<Integer> singerIds = singerSongs.stream().map(SingerSong::getSingerId).collect(Collectors.toList());

            LambdaQueryWrapper<Singer> singerLambdaQueryWrapper = new LambdaQueryWrapper<>();
            singerLambdaQueryWrapper.in(Singer::getId, singerIds);
            List<Singer> singers = singerMapper.selectList(singerLambdaQueryWrapper);
            item.setSingers(singers);

            LambdaQueryWrapper<SongCollect> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(SongCollect::getSongId, item.getId());
            Integer count = songCollectMapper.selectCount(queryWrapper1);
            item.setIsLove(count > 0);
            return item;
        }).collect(Collectors.toList());
    }

    @Override
    public IPage<Song> search(String keyword, Integer pageSize, Integer pageNum) {
        Page<Song> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Song> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Song::getName, keyword);
        queryWrapper.orderByDesc(Song::getCreateTime);
        IPage<Song> iPage = songMapper.selectPage(page, queryWrapper);
        List<Song> list = iPage.getRecords();
        list = list.stream().map(item -> {

            LambdaQueryWrapper<SingerSong> singerSongLambdaQueryWrapper = new LambdaQueryWrapper<>();
            singerSongLambdaQueryWrapper.eq(SingerSong::getSongId, item.getId());
            List<SingerSong> singerSongs = singerSongMapper.selectList(singerSongLambdaQueryWrapper);

            List<Integer> singerIds = singerSongs.stream().map(SingerSong::getSingerId).collect(Collectors.toList());

            LambdaQueryWrapper<Singer> singerLambdaQueryWrapper = new LambdaQueryWrapper<>();
            singerLambdaQueryWrapper.in(Singer::getId, singerIds);
            List<Singer> singers = singerMapper.selectList(singerLambdaQueryWrapper);
            item.setSingers(singers);

            LambdaQueryWrapper<SongCollect> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(SongCollect::getSongId, item.getId());
            Integer count = songCollectMapper.selectCount(queryWrapper1);

            item.setIsLove(count > 0);
            return item;
        }).collect(Collectors.toList());

        iPage.setRecords(list);
        return iPage;
    }
}
