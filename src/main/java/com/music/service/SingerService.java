package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.entity.Singer;

import java.util.Map;

public interface SingerService extends IService<Singer> {
    Map<String, Object> getSinger(Integer singerId);
}
