package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.entity.SongListComment;
import com.music.entity.User;
import com.music.mapper.SongListCommentMapper;
import com.music.mapper.UserMapper;
import com.music.service.SongListCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongListCommentServiceImpl extends ServiceImpl<SongListCommentMapper, SongListComment> implements SongListCommentService {

    @Autowired
    private SongListCommentMapper songListCommentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<SongListComment> getBySongListId(Integer songListId, Integer pageSize, Integer pageNum) {
        Page<SongListComment> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<SongListComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SongListComment::getSongListId, songListId);
        queryWrapper.orderByDesc(SongListComment::getCreateTime);
        IPage<SongListComment> iPage = songListCommentMapper.selectPage(page, queryWrapper);
        iPage.setRecords(iPage.getRecords().stream().map(item -> {
            User user = userMapper.selectById(item.getUserId());
            user.setPassword("");
            user.setPhone("");
            item.setUser(user);
            return item;
        }).collect(Collectors.toList()));
        return iPage;
    }

    @Override
    public List<SongListComment> getWonderfulSongListCommentById(Integer songListId) {
        LambdaQueryWrapper<SongListComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SongListComment::getSongListId, songListId);
        queryWrapper.orderByDesc(SongListComment::getStar);
        List<SongListComment> songListComments = songListCommentMapper.selectList(queryWrapper);
        if(songListComments.size() > 10){
            songListComments = songListComments.subList(0, 10);
        }else {
            songListComments = songListComments.subList(0, songListComments.size());
        }
        songListComments = songListComments.stream().map(item -> {
            User user = userMapper.selectById(item.getUserId());
            user.setPassword("");
            user.setPhone("");
            item.setUser(user);
            return item;
        }).collect(Collectors.toList());
        return songListComments;
    }

    @Override
    public boolean star(Integer songListCommentId) {
        SongListComment songListComment = songListCommentMapper.selectById(songListCommentId);
        songListComment.setStar(songListComment.getStar() + 1);
        int i = songListCommentMapper.updateById(songListComment);
        return i == 1;
    }

    @Override
    public boolean addComment(SongListComment songListComment, Integer userId) {
        songListComment.setUserId(userId);
        songListComment.setStar(0);
        songListComment.setCreateTime(LocalDateTime.now());
        System.out.println(songListComment);
        int insert = songListCommentMapper.insert(songListComment);
        return insert == 1;
    }
}
