package com.wh.searchall.service.impl;

import com.wh.searchall.dao.UserDao;
import com.wh.searchall.pojo.User;
import com.wh.searchall.service.UserService;
import com.wh.searchall.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/13 10:07
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User checkUser(String username, String password) {
        User user = userDao.queryByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
