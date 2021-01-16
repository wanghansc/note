package com.wh.searchall.dao;

import com.wh.searchall.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/14 20:26
 **/
@Mapper
//@Repository
public interface TypeDao {
    List<Type> getAllType();

    Type getTypeByName(String name);

    int saveType(Type type);

    Type getType(Long id);

    int updateType(Type type);

    int deleteType(Long id);
}
