package com.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.entity.Admin;
import com.music.mapper.AdminMapper;
import com.music.service.AdminService;
import com.music.utils.MD5Utils;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public Admin login(Admin admin) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        String username = admin.getUsername();
        String password = admin.getPassword();
        queryWrapper.eq(username != null, Admin::getUsername, username);
        queryWrapper.eq(password != null, Admin::getPassword, MD5Utils.md5(password));
        return this.getOne(queryWrapper);
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        Admin admin1 = this.getById(admin.getId());
        LambdaUpdateWrapper<Admin> updateWrapper = new LambdaUpdateWrapper<>();
        String username = admin.getUsername();
        String password = admin.getPassword();
        updateWrapper.eq(Admin::getId, admin1.getId());
        updateWrapper.set(username != null && !"".equals(username), Admin::getUsername, username);
        updateWrapper.set(password != null && !"".equals(password), Admin::getPassword, MD5Utils.md5(password));
        this.update(admin1, updateWrapper);
        admin1 = this.getById(admin.getId());
        return admin1;
    }
}
