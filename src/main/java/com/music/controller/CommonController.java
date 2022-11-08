package com.music.controller;

import com.music.common.R;
import com.music.utils.NoAuthorization;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class CommonController {

    private final static String PATH = System.getProperty("user.dir") + "/files/";

    @NoAuthorization
    @PostMapping("/uploadAvatar")
    public R upload(MultipartFile file){
        File file1 = new File(PATH);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID().toString() + suffix;
        try {
            file.transferTo(new File(PATH + filename));
        } catch (IOException e) {
        }
//        return R.success("http://127.0.0.1:8099/file/download/" + filename);
        return R.success("http://10.10.30.123:8099/file/download/" + filename);
    }


    @NoAuthorization
    @GetMapping("/download/{filename}")
    public void download(@PathVariable("filename") String filename, HttpServletResponse response){
        File file1 = new File(PATH);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(PATH + filename));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/octet-stream");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
        }
    }
}
