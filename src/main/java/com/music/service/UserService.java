package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    User login(User user);

    String register(User user);

    List<User> findByPhoneOrUsername(User user);

    Integer checkToken(String token);

    User updateByUserId(User user);

    User findByPhone(String phone);
}
