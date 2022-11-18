package com.music.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.config.RSAConfig;
import com.music.entity.User;
import com.music.exception.UserException;
import com.music.mapper.UserMapper;
import com.music.service.UserService;
import com.music.utils.JwtUtils;
import com.music.utils.MD5Utils;
import com.music.utils.UserThreadLocal;
import com.music.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RSAConfig rsaConfig;

    @Override
    public User login(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        String username = user.getUsername();
        String password = user.getPassword();
        queryWrapper.eq(username != null, User::getUsername, username);
        queryWrapper.eq(password != null, User::getPassword, MD5Utils.md5(password));
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public String register(User user) {
        String phone = user.getPhone();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        User user1 = userMapper.selectOne(queryWrapper);
        if(user1 != null){
            return "手机号已存在";
        }
        String username = "user" + ValidateCodeUtils.generateValidateCode4String(6);
        user.setUsername(username);
        user.setPassword("123456");
        if(phone == null || "".equals(phone)){
            return "请输入手机号";
        }
        user.setAvatar("/img/user.jpg");
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
        return "注册成功";

    }

    @Override
    public List<User> findByPhoneOrUsername(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        String phone = user.getPhone();
        String username = user.getUsername();
        queryWrapper.eq(phone != null, User::getPhone, phone).or().eq(username != null, User::getUsername, username);
        List<User> users = userMapper.selectList(queryWrapper);
        return users.stream().peek((item)-> item.setPassword("")).collect(Collectors.toList());
    }

    @Override
    public Integer checkToken(String token) {
        Map<String, Object> map = JwtUtils.checkToken(token, this.rsaConfig.getPublishStr());
        if (CollUtil.isNotEmpty(map)) {
            return Convert.toInt(map.get("userId"));
        }
        return null;
    }

    @Override
    public String checkAdminToken(String token) {
        Map<String, Object> map = JwtUtils.checkToken(token, this.rsaConfig.getPublishStr());
        if (CollUtil.isNotEmpty(map)) {
            return map.get("userId").toString();
        }
        return null;
    }

    @Override
    public User updateByUserId(User user) throws Exception {
        Integer userId = UserThreadLocal.get();
        User user1 = userMapper.selectById(userId);
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        String username = user.getUsername();
        String password = user.getPassword();
        String avatar = user.getAvatar();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(username != null && !"".equals(username), User::getUsername, username);
        User one = this.getOne(queryWrapper);
        if (one != null && !userId.equals(one.getId())){
            throw new UserException("该用户名已存在");
        }
        updateWrapper.eq(User::getId, userId);
        updateWrapper.set(username != null && !"".equals(username), User::getUsername, username);
        updateWrapper.set(password != null && !"".equals(password), User::getPassword, MD5Utils.md5(password));
        updateWrapper.set(avatar != null && !"".equals(avatar), User::getAvatar, avatar);
        userMapper.update(user1, updateWrapper);
        user1 = userMapper.selectById(userId);
        return user1;
    }

    @Override
    public User findByPhone(String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(phone != null, User::getPhone, phone);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<User> getUserByPage(Integer pageSize, Integer pageNum, String keyword) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (!"".equals(keyword) && keyword != null){
            queryWrapper.like(User::getUsername, keyword)
                    .or().like(User::getPhone, keyword);
        }
        queryWrapper.orderByDesc(User::getCreateTime);
        Page<User> userPage = this.page(page, queryWrapper);
        userPage.setRecords(userPage.getRecords().stream().map(item -> {
            item.setPassword("");
            return item;
        }).collect(Collectors.toList()));
        return userPage;
    }
}
