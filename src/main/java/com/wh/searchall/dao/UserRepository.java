package com.wh.searchall.dao;

import com.wh.searchall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/13 10:08
 **/
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username, String password);
}
