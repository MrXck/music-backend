package com.music.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.music.common.R;
import com.music.entity.SongComment;
import com.music.service.SongCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/songComment")
public class SongCommentController {

    @Autowired
    private SongCommentService songCommentService;

    @GetMapping("/{songId}")
    public R getComment(@PathVariable("songId") Integer songId, Integer pageSize, Integer pageNum){
        IPage<SongComment> iPage = songCommentService.getComment(songId, pageSize, pageNum);
        return R.success(iPage);
    }

    @GetMapping("/star/{songCommentId}")
    public R star(@PathVariable("songCommentId") Integer songCommentId){
        boolean star = songCommentService.star(songCommentId);
        if(star){
            return R.success("点赞成功");
        }
        return R.error("点赞失败");
    }
}
