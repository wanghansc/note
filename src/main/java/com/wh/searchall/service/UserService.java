package com.wh.searchall.service;

import com.wh.searchall.pojo.User;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/13 10:06
 **/
public interface UserService {
    User checkUser(String username, String password);
}
