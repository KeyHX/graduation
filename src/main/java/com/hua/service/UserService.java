package com.hua.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hua.entity.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: KeyHX
 * @Date: 2023/01/10/17:10
 * @Function:
 */

public interface UserService extends IService<User> {
    Boolean verfiyUser(User user);

    boolean createUser(User user);

    IPage<User> queryAllUser(Integer pageNo, Integer size, String keyWord);
}
