package com.hua.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hua.entity.User;
import com.hua.result.CommonResult;
import com.hua.service.UserService;
import com.hua.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.nio.cs.US_ASCII;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: KeyHX
 * @Date: 2023/01/10/17:11
 * @Function:
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    /**
     * 注册账号
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public CommonResult register(@RequestBody User user){
        boolean result = userService.createUser(user);
        if (result){
            return CommonResult.success("创建成功！！");
        }else {
            return CommonResult.failed("用户名已存在！！");
        }
    }

    /**
     * 查询所有用户
     * @param
     * @return
     */
    @RequestMapping("/queryAllUser")
    public CommonResult queryAllUser(@RequestParam Integer pageNo,
                                     @RequestParam Integer size,
                                     @RequestParam String keyWord){
        IPage<User> userIPage = userService.queryAllUser(pageNo, size, keyWord);
        return CommonResult.success(userIPage);
    }

    @RequestMapping("/unauthorized")
    public CommonResult unauthorized(HttpServletRequest request){
        return CommonResult.unauthorized(null);
    }


}
