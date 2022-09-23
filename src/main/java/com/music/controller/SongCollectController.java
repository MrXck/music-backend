package com.music.controller;

import com.music.common.R;
import com.music.entity.Song;
import com.music.service.SongCollectService;
import com.music.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/songCollect")
public class SongCollectController {

    @Autowired
    private SongCollectService songCollectService;

    @GetMapping("/getMyLoveMusic")
    public R getMyLoveMusic(){
        Integer userId = UserThreadLocal.get();
        List<Song> list = songCollectService.getByUserId(userId);
        return R.success(list);
    }

    @GetMapping("/removeLoveMusic/{songId}")
    public R removeLoveMusic(@PathVariable("songId") Integer songId){
        Integer userId = UserThreadLocal.get();
        songCollectService.removeLoveMusic(songId, userId);
        return R.success("取消成功");
    }

    @GetMapping("/addLoveMusic/{songId}")
    public R addLoveMusic(@PathVariable("songId") Integer songId){
        Integer userId = UserThreadLocal.get();
        boolean result = songCollectService.addLoveMusic(songId, userId);
        if(result){
            return R.success("添加成功");
        }
        return R.error("已收藏");
    }
}
