package com.wh.searchall.service.impl;

import com.wh.searchall.dao.UserRepository;
import com.wh.searchall.pojo.User;
import com.wh.searchall.service.UserService;
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
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user;
    }
}
