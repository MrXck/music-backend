package com.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.common.R;
import com.music.entity.Song;
import com.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping("/newSong")
    public R newSong(){
        List<Song> list = songService.getNewSong();
        return R.success(list);
    }

    @GetMapping("/search")
    public R search(String keyword, Integer pageSize, Integer pageNum){
        IPage<Song> iPage = songService.search(keyword, pageSize, pageNum);
        return R.success(iPage);
    }

}
