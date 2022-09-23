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
import com.music.service.SingerService;
import com.music.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {

    @Autowired
    private SingerMapper singerMapper;

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SongCollectMapper songCollectMapper;

    @Autowired
    private SingerSongMapper singerSongMapper;

    @Override
    public Map<String, Object> getSinger(Integer singerId) {

        Integer userId = UserThreadLocal.get();

        Singer singer = singerMapper.selectById(singerId);

        LambdaQueryWrapper<SingerSong> singerSongLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        singerSongLambdaQueryWrapper1.eq(SingerSong::getSingerId, singerId);
        List<SingerSong> singerSongs1 = singerSongMapper.selectList(singerSongLambdaQueryWrapper1);
        List<Integer> songIds = singerSongs1.stream().map(SingerSong::getSongId).collect(Collectors.toList());
        System.out.println(songIds);

        List<Song> list = new ArrayList<>();

        if(songIds.size() > 0){
            LambdaQueryWrapper<Song> songLambdaQueryWrapper = new LambdaQueryWrapper<>();
            songLambdaQueryWrapper.in(Song::getId, songIds);
            songLambdaQueryWrapper.orderByDesc(Song::getCreateTime);
            list = songMapper.selectList(songLambdaQueryWrapper);

            list = list.stream().map(item -> {
                LambdaQueryWrapper<SongCollect> songCollectLambdaQueryWrapper = new LambdaQueryWrapper<>();
                songCollectLambdaQueryWrapper.eq(SongCollect::getUserId, userId);
                songCollectLambdaQueryWrapper.eq(SongCollect::getSongId, item.getId());
                Integer count = songCollectMapper.selectCount(songCollectLambdaQueryWrapper);
                item.setIsLove(count > 0);


                LambdaQueryWrapper<SingerSong> singerSongLambdaQueryWrapper = new LambdaQueryWrapper<>();
                singerSongLambdaQueryWrapper.eq(SingerSong::getSongId, item.getId());
                List<SingerSong> singerSongs = singerSongMapper.selectList(singerSongLambdaQueryWrapper);

                List<Integer> singerIds = singerSongs.stream().map(SingerSong::getSingerId).collect(Collectors.toList());

                LambdaQueryWrapper<Singer> singerLambdaQueryWrapper = new LambdaQueryWrapper<>();
                singerLambdaQueryWrapper.in(Singer::getId, singerIds);
                List<Singer> singers = singerMapper.selectList(singerLambdaQueryWrapper);
                item.setSingers(singers);

                return item;
            }).collect(Collectors.toList());
        }



        Map<String, Object> map = new HashMap<>();
        map.put("singer", singer);
        map.put("songs", list);
        return map;
    }
}
