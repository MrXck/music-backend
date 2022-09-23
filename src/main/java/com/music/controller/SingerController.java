package com.music.controller;

import com.music.common.R;
import com.music.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/singer")
public class SingerController {

    @Autowired
    private SingerService singerService;

    @GetMapping("/{singerId}")
    public R getSinger(@PathVariable("singerId") Integer singerId){
        Map<String, Object> map = singerService.getSinger(singerId);
        return R.success(map);
    }

}
