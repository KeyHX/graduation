package com.hua.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hua.entity.User;
import com.hua.exception.ReportMessageException;
import com.hua.mapper.UserMapper;
import com.hua.result.CommonResult;
import com.hua.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: KeyHX
 * @Date: 2023/01/10/17:11
 * @Function:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public Boolean verfiyUser(User user) {
        String name = user.getUsername();
        String password = user.getPassword();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        User queryUser = this.getOne(wrapper);
        if (queryUser == null){
            return false;
        }
        if (bCryptPasswordEncoder.matches(password, queryUser.getPassword())) {
            // 密码匹配
            return true;
        } else {
            // 密码不匹配
            return false;
        }


    }

    @Override
    public boolean createUser(User user) {
        //获取明文密码
        String password = user.getPassword();
        // 使用 BCryptPasswordEncoder 进行加密
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setCreateTime(LocalDateTime.now());
        user.setDeleted(0);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        QueryWrapper<User> userQueryWrapper = wrapper.eq("name", user.getUsername());
        User getUser = this.getOne(wrapper);
        if (getUser != null){
            return false;
        }
        boolean save = this.save(user);
        return save;
    }

    @Override
    public IPage<User> queryAllUser(Integer pageNo, Integer size, String keyWord) {
        Page<User> page = new Page<>(pageNo, size);
        keyWord = StringUtils.lowerCase(keyWord);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("name as username,create_time")
                .like("lower(name)",keyWord);
        Page<User> userPage = this.page(page, wrapper);
        return userPage;
    }
}
