package com.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.common.R;
import com.music.entity.SongListComment;
import com.music.service.SongListCommentService;
import com.music.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songListComment")
public class SongListCommentController {

    @Autowired
    private SongListCommentService songListCommentService;

    @GetMapping("/{songListId}")
    public R getById(@PathVariable("songListId") Integer songListId, Integer pageSize, Integer pageNum){
        IPage<SongListComment> iPage = songListCommentService.getBySongListId(songListId, pageSize, pageNum);
        return R.success(iPage);
    }

    @GetMapping("/getWonderfulSongListComment/{songListId}")
    public R getWonderfulSongListCommentById(@PathVariable("songListId") Integer songListId){
        List<SongListComment> list = songListCommentService.getWonderfulSongListCommentById(songListId);
        return R.success(list);
    }

    @GetMapping("/star/{songListCommentId}")
    public R star(@PathVariable("songListCommentId") Integer songListCommentId){
        boolean star = songListCommentService.star(songListCommentId);
        if(star){
            return R.success("点赞成功");
        }
        return R.error("点赞失败");
    }

    @PostMapping("/addComment")
    public R addComment(@RequestBody SongListComment songListComment){
        Integer userId = UserThreadLocal.get();
        boolean result = songListCommentService.addComment(songListComment, userId);
        if(result){
            return R.success("评论成功");
        }
        return R.error("评论失败");
    }

}
