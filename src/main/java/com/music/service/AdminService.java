package com.music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.music.entity.Admin;

public interface AdminService extends IService<Admin> {
    Admin login(Admin admin);

    Admin updateAdmin(Admin admin);
}
