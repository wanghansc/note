package com.wh.searchall.dao;

import com.wh.searchall.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/14 15:51
 **/
@Mapper
@Repository
public interface UserDao {
    User queryByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
