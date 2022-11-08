package com.music.handler;

import com.music.common.R;
import com.music.exception.LoginException;
import com.music.exception.SaveSongException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * @author xck
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    public R<String> handleBindException(IOException ex){
        return R.error("文件不存在");
    }

    @ExceptionHandler(Exception.class)
    public R<String> handleBindException(Exception ex) {
        ex.printStackTrace();
        return R.error("操作失败");
    }

    @ExceptionHandler(SaveSongException.class)
    public R<String> handleBindException(SaveSongException ex) {
        return R.error("保存歌曲时歌曲的歌手数至少为1");
    }

    @ExceptionHandler(LoginException.class)
    public R<String> handleBindException(LoginException ex) {
        return R.error("用户名或密码错误");
    }

}
