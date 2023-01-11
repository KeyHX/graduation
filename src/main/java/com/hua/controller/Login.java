package com.hua.controller;

import com.hua.entity.User;
import com.hua.result.CommonResult;
import com.hua.service.UserService;
import com.hua.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: KeyHX
 * @Date: 2023/01/11/16:58
 * @Function:
 */
@RestController
@RequestMapping("/api")
public class Login {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public CommonResult login(@RequestBody User user){
        String token = JWTUtil.createToken(user);
        user.setToken(token);
        Boolean result = userService.verfiyUser(user);
        if (result){
            return CommonResult.success(user);
        }else {
            return CommonResult.failed("用户名或者密码不对！");
        }

    }
}
