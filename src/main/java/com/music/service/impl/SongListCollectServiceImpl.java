package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.entity.SongList;
import com.music.entity.SongListCollect;
import com.music.mapper.SongListCollectMapper;
import com.music.mapper.SongListMapper;
import com.music.service.SongListCollectService;
import com.music.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongListCollectServiceImpl extends ServiceImpl<SongListCollectMapper, SongListCollect> implements SongListCollectService {

    @Autowired
    private SongListCollectMapper songListCollectMapper;

    @Autowired
    private SongListMapper songListMapper;

    @Override
    public boolean addSongListCollect(Integer id) {
        Integer userId = UserThreadLocal.get();

        LambdaQueryWrapper<SongListCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SongListCollect::getSongListId, id);
        queryWrapper.eq(SongListCollect::getUserId, userId);
        Integer count1 = songListCollectMapper.selectCount(queryWrapper);
        if(count1 > 0){
            return false;
        }

        SongListCollect songListCollect = new SongListCollect();
        songListCollect.setSongListId(id);
        songListCollect.setUserId(userId);
        int count = songListCollectMapper.insert(songListCollect);
        return count > 0;
    }

    @Override
    public boolean notSongListCollect(Integer id) {
        Integer userId = UserThreadLocal.get();

        LambdaQueryWrapper<SongListCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SongListCollect::getSongListId, id);
        queryWrapper.eq(SongListCollect::getUserId, userId);
        SongListCollect songListCollect = songListCollectMapper.selectOne(queryWrapper);
        int count = songListCollectMapper.deleteById(songListCollect.getId());
        return count > 0;
    }

    @Override
    public List<SongList> getSongListCollect() {
        Integer userId = UserThreadLocal.get();

        LambdaQueryWrapper<SongListCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SongListCollect::getUserId, userId);
        List<SongListCollect> songListCollects = songListCollectMapper.selectList(queryWrapper);
        return songListCollects.stream().map(item -> songListMapper.selectById(item.getSongListId())).collect(Collectors.toList());
    }
}
