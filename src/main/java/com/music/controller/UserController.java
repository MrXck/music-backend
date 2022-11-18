package com.music.controller;

import com.music.common.R;
import com.music.config.RSAConfig;
import com.music.entity.User;
import com.music.service.UserService;
import com.music.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RSAConfig rsaConfig;

    @Autowired
    private RedisTemplate redisTemplate;

    @NoAuthorization
    @PostMapping("/login")
    public R login(@RequestBody User user){
        User user1 = userService.login(user);
        if(user1 != null){
            user1.setPassword("");
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user1.getId()); //将用户id存入到token中
            //生成token，算法采用RSA非对称加密
            String token = JwtUtils.createToken(claims, this.rsaConfig.getPrivateStr(), 720);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("user", user1);
            redisTemplate.opsForValue().set(user1.getId().toString(), token, 30, TimeUnit.MINUTES);
            return R.success(map);
        }
        return R.error("用户名或密码错误");
    }

    @NoAuthorization
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user){
        String phone = user.getPhone();
        boolean isChinaPhoneLegal = PhoneFormatCheckUtils.isChinaPhoneLegal(phone);
        if(!isChinaPhoneLegal){
            return R.error("请输入正确的手机号");
        }
        try {
            String code = (String) redisTemplate.opsForValue().get(phone);
            if(code != null){
                return R.error("短信还未失效, 请稍后重试");
            }
            String validateCode = ValidateCodeUtils.generateValidateCode(4).toString();
            SMSUtils.sendSMS(phone, validateCode);
            redisTemplate.opsForValue().set(phone, validateCode, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("发送失败");
        }
        return R.success("发送成功");
    }

    @NoAuthorization
    @PostMapping("/loginBySms")
    public R loginBySms(@RequestBody User user, String code){
        String phone = user.getPhone();
        boolean isChinaPhoneLegal = PhoneFormatCheckUtils.isChinaPhoneLegal(phone);
        if(!isChinaPhoneLegal){
            return R.error("请输入正确的手机号");
        }
        User user1 = userService.findByPhone(phone);
        if(user1 != null){
            String validate = (String) redisTemplate.opsForValue().get(phone);
            if(validate != null && !"".equals(validate)){
                if(validate.equals(code)){
                    user1.setPassword("");
                    Map<String, Object> claims = new HashMap<>();
                    claims.put("userId", user1.getId()); //将用户id存入到token中
                    //生成token，算法采用RSA非对称加密
                    String token = JwtUtils.createToken(claims, this.rsaConfig.getPrivateStr(), 720);
                    Map<String, Object> map = new HashMap<>();
                    map.put("token", token);
                    map.put("user", user1);
                    redisTemplate.opsForValue().set(user1.getId().toString(), token, 30, TimeUnit.MINUTES);
                    return R.success(map);
                }
            }
        }
        return R.error("验证码错误或失效");
    }

    @NoAuthorization
    @PostMapping("/register")
    public R register(@RequestBody User user, String code){
        String phone = user.getPhone();
        boolean isChinaPhoneLegal = PhoneFormatCheckUtils.isChinaPhoneLegal(phone);
        if(!isChinaPhoneLegal){
            return R.error("请输入正确的手机号");
        }
        String validate = (String) redisTemplate.opsForValue().get(phone);
        if(validate != null && !"".equals(validate)){
            if(validate.equals(code)){
                String result = userService.register(user);
                if(result.equals("注册成功")){
                    return R.success("注册成功");
                }
                return R.error(result);
            }
        }
        return R.error("验证码错误或失效");
    }

    @PostMapping("/find")
    public R findByPhoneOrUsername(@RequestBody User user){
        List<User> users = userService.findByPhoneOrUsername(user);
        return R.success(users);
    }

    @PostMapping("/update")
    public R update(@RequestBody User user) throws Exception{
        User user1 = userService.updateByUserId(user);
        return R.success(user1);
    }


}
