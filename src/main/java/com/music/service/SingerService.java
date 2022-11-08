package com.music.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.music.entity.Singer;

import java.util.Map;

public interface SingerService extends IService<Singer> {
    Map<String, Object> getSinger(Integer singerId);

    IPage<Singer> getSingerByPage(Integer pageSize, Integer pageNum, String keyword);

    void updateOrSave(Singer singer);

    void deleteSinger(Integer id);
}
