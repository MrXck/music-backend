package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.entity.*;
import com.music.mapper.*;
import com.music.service.SongListService;
import com.music.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {

    @Autowired
    private SongListMapper songListMapper;

    @Autowired
    private SongSongListMapper songSongListMapper;

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SingerMapper singerMapper;

    @Autowired
    private SongCollectMapper songCollectMapper;

    @Autowired
    private SongListCollectMapper songListCollectMapper;

    @Autowired
    private SingerSongMapper singerSongMapper;

    @Override
    public List<SongList> getRecommend() {
        LambdaQueryWrapper<SongList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SongList::getCreateTime);
        List<SongList> songLists = songListMapper.selectList(queryWrapper);
        if(songLists.size() > 10){
            songLists = songLists.subList(0, 10);
        }else {
            songLists = songLists.subList(0, songLists.size());
        }
        return songLists;
    }

    @Override
    public SongList getSongList(Integer id) {
        Integer userId = UserThreadLocal.get();

        LambdaQueryWrapper<SongSongList> songSongListLambdaQueryWrapper = new LambdaQueryWrapper<>();
        songSongListLambdaQueryWrapper.eq(SongSongList::getSongListId, id);
        List<SongSongList> songSongLists = songSongListMapper.selectList(songSongListLambdaQueryWrapper);

        SongList songList = songListMapper.selectById(id);
        List<Song> songs = new ArrayList<>();
        List<Integer> list = songSongLists.stream().map(SongSongList::getSongId).collect(Collectors.toList());
        LambdaQueryWrapper<Song> songLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(list.size() > 0){
            songLambdaQueryWrapper.in(Song::getId, list);
            songs = songMapper.selectList(songLambdaQueryWrapper);
            songs = songs.stream().map(item -> {
                LambdaQueryWrapper<SongCollect> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SongCollect::getSongId, item.getId());
                queryWrapper.eq(SongCollect::getUserId, userId);
                Integer count = songCollectMapper.selectCount(queryWrapper);

                LambdaQueryWrapper<SingerSong> singerSongLambdaQueryWrapper = new LambdaQueryWrapper<>();
                singerSongLambdaQueryWrapper.eq(SingerSong::getSongId, item.getId());
                List<SingerSong> singerSongs = singerSongMapper.selectList(singerSongLambdaQueryWrapper);

                List<Integer> singerIds = singerSongs.stream().map(SingerSong::getSingerId).collect(Collectors.toList());

                LambdaQueryWrapper<Singer> singerLambdaQueryWrapper = new LambdaQueryWrapper<>();
                singerLambdaQueryWrapper.in(Singer::getId, singerIds);
                List<Singer> singers = singerMapper.selectList(singerLambdaQueryWrapper);
                item.setSingers(singers);

                item.setIsLove(count > 0);
                return item;
            }).collect(Collectors.toList());
        }

        User user = userMapper.selectById(songList.getCreator());
        user.setPassword("");
        user.setPhone("");

        LambdaQueryWrapper<SongListCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SongListCollect::getSongListId, id);
        queryWrapper.eq(SongListCollect::getUserId, userId);
        Integer count = songListCollectMapper.selectCount(queryWrapper);

        songList.setSongs(songs);
        songList.setUser(user);
        songList.setIsCollect(count > 0);
        return songList;
    }

    @Override
    public IPage<SongList> search(String keyword, Integer pageSize, Integer pageNum) {
        Page<SongList> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SongList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(SongList::getName, keyword);
        queryWrapper.orderByDesc(SongList::getCreateTime);
        IPage<SongList> iPage = songListMapper.selectPage(page, queryWrapper);
        List<SongList> list = iPage.getRecords();

        list = list.stream().map(item -> {
            User user = userMapper.selectById(item.getCreator());
            user.setPassword("");
            user.setPhone("");
            item.setUser(user);
            LambdaQueryWrapper<SongListCollect> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(SongListCollect::getSongListId, item.getId());
            item.setSongCount(songListCollectMapper.selectCount(queryWrapper1));
            return item;
        }).collect(Collectors.toList());

        iPage.setRecords(list);
        return iPage;
    }

    @Override
    public void delete(Integer id) {
        LambdaQueryWrapper<SongListCollect> songListCollectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        songListCollectLambdaQueryWrapper.eq(SongListCollect::getSongListId, id);
        songListCollectMapper.delete(songListCollectLambdaQueryWrapper);

        this.removeById(id);
    }

    @Override
    public void updateSongList(SongList songList) {
        if (songList.getId() == null){
            songList.setCreateTime(LocalDateTime.now());
            this.save(songList);
        } else {
            this.updateById(songList);
        }

        List<Song> songs = songList.getSongs();
        List<Integer> songIds = songs.stream().map(Song::getId).collect(Collectors.toList());

        if (songIds.size() > 0){
            songSongListMapper.deleteBatchIds(songIds);
        }

        for (Song song : songs) {
            SongSongList songSongList = new SongSongList();
            songSongList.setSongListId(songList.getId());
            songSongList.setSongId(song.getId());
            songSongListMapper.insert(songSongList);
        }
    }
}
