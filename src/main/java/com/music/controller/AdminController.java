package com.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.common.R;
import com.music.config.RSAConfig;
import com.music.entity.Admin;
import com.music.entity.Singer;
import com.music.entity.Song;
import com.music.entity.User;
import com.music.exception.SaveSongException;
import com.music.service.*;
import com.music.utils.AdminAuthorization;
import com.music.utils.JwtUtils;
import com.music.utils.NoAuthorization;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final SingerService singerService;

    private final SongService songService;

    private final UserService userService;

    private final AdminService adminService;

    private final RSAConfig rsaConfig;

    private final RedisTemplate redisTemplate;

    public AdminController(SingerService singerService, RedisTemplate redisTemplate, RSAConfig rsaConfig, SongService songService, UserService userService, AdminService adminService) {
        this.singerService = singerService;
        this.songService = songService;
        this.userService = userService;
        this.adminService = adminService;
        this.rsaConfig = rsaConfig;
        this.redisTemplate = redisTemplate;
    }

    @AdminAuthorization
    @GetMapping("/user/page")
    public R<IPage<User>> userPage(Integer pageSize, Integer pageNum, String keyword){
        return R.success(userService.getUserByPage(pageSize, pageNum, keyword));
    }

    @AdminAuthorization
    @GetMapping("/user/all")
    public R<List<User>> userAll(){
        return R.success(userService.list());
    }

    @AdminAuthorization
    @GetMapping("/user/delete/{id}")
    public R<String> userDelete(@PathVariable("id") Integer id){
        userService.removeById(id);
        return R.success("");
    }

    @AdminAuthorization
    @PostMapping("/singer/update")
    public R<String> singerUpdateOrSave(@RequestBody Singer singer){
        singerService.updateOrSave(singer);
        return R.success("");
    }

    @AdminAuthorization
    @PostMapping("/singer/delete/{id}")
    public R<String> singerDelete(@PathVariable("id") Integer id){
        singerService.deleteSinger(id);
        return R.success("");
    }

    @AdminAuthorization
    @PostMapping("/song/update")
    public R<String> songUpdate(@RequestBody Song song) throws SaveSongException {
        songService.insertOrUpdate(song);
        return R.success("");
    }

    @AdminAuthorization
    @PostMapping("/song/delete/{id}")
    public R<String> songDelete(@PathVariable("id") Integer id){
        songService.deleteSong(id);
        return R.success("");
    }

    @NoAuthorization
    @PostMapping("/login")
    public R<Map> login(@RequestBody Admin admin){
        Admin admin1 = adminService.login(admin);
        if(admin1 != null){
            admin1.setPassword("");
            Map<String, Object> claims = new HashMap<>(16);
            claims.put("userId", "admin");
            //生成token，算法采用RSA非对称加密
            String token = JwtUtils.createToken(claims, this.rsaConfig.getPrivateStr(), 720);
            Map<String, Object> map = new HashMap<>(16);
            map.put("token", token);
            map.put("user", admin1);
            redisTemplate.opsForValue().set("admin", token, 30, TimeUnit.MINUTES);
            return R.success(map);
        }
        return R.error("用户名或密码错误");
    }

    @AdminAuthorization
    @PostMapping("/update")
    public R<Admin> update(@RequestBody Admin admin){
        return R.success(adminService.updateAdmin(admin));
    }

}
