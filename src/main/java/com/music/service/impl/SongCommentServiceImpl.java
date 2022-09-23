package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.entity.SongComment;
import com.music.entity.User;
import com.music.mapper.SongCommentMapper;
import com.music.mapper.UserMapper;
import com.music.service.SongCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SongCommentServiceImpl extends ServiceImpl<SongCommentMapper, SongComment> implements SongCommentService {

    @Autowired
    private SongCommentMapper songCommentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<SongComment> getComment(Integer songId, Integer pageSize, Integer pageNum) {
        Page<SongComment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SongComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SongComment::getSongId, songId);
        queryWrapper.orderByDesc(SongComment::getCreateTime);
        IPage<SongComment> iPage = songCommentMapper.selectPage(page, queryWrapper);

        List<SongComment> list = iPage.getRecords();

        iPage.setRecords(list.stream().map(item -> {
            User user = userMapper.selectById(item.getUserId());
            user.setPhone("");
            user.setPassword("");
            item.setUser(user);
            return item;
        }).collect(Collectors.toList()));

        return iPage;
    }

    @Override
    public boolean star(Integer songCommentId) {
        SongComment songListComment = songCommentMapper.selectById(songCommentId);
        songListComment.setStar(songListComment.getStar() + 1);
        int i = songCommentMapper.updateById(songListComment);
        return i == 1;
    }
}
