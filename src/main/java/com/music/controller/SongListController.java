package com.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.common.R;
import com.music.entity.SongList;
import com.music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songList")
public class SongListController {

    @Autowired
    private SongListService songListService;

    @GetMapping("/getRecommend")
    public R getRecommend(){
        List<SongList> songLists = songListService.getRecommend();
        return R.success(songLists);
    }

    @GetMapping("/{songListId}")
    public R getSongList(@PathVariable("songListId") Integer id){
        SongList songList = songListService.getSongList(id);
        return R.success(songList);
    }

    @GetMapping("/search")
    public R search(String keyword, Integer pageSize, Integer pageNum){
        IPage<SongList> iPage = songListService.search(keyword, pageSize, pageNum);
        return R.success(iPage);
    }

    @PostMapping("/delete/{id}")
    public R<String> delete(@PathVariable("id") Integer id){
        songListService.delete(id);
        return R.success("");
    }

    @PostMapping("/add")
    public R<String> add(@RequestBody SongList songList){
        songListService.save(songList);
        return R.success("");
    }

    @PostMapping("/update")
    public R<String> update(@RequestBody SongList songList){
        songListService.updateSongList(songList);
        return R.success("");
    }

}
