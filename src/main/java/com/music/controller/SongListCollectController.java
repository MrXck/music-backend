package com.music.controller;

import com.music.common.R;
import com.music.entity.SongList;
import com.music.service.SongListCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/songListCollect")
public class SongListCollectController {

    @Autowired
    private SongListCollectService songListCollectService;


    @GetMapping("/addSongListCollect/{songListId}")
    public R addSongListCollect(@PathVariable("songListId") Integer id){
        boolean result = songListCollectService.addSongListCollect(id);
        if(result){
            return R.success("收藏成功");
        }
        return R.error("已收藏");
    }

    @GetMapping("/notSongListCollect/{songListId}")
    public R notSongListCollect(@PathVariable("songListId") Integer id){
        boolean result = songListCollectService.notSongListCollect(id);
        if(result){
            return R.success("取消成功");
        }
        return R.error("取消失败");
    }

    @GetMapping("/getSongListCollect")
    public R getSongListCollect(){
        List<SongList> list = songListCollectService.getSongListCollect();
        return R.success(list);
    }
}
