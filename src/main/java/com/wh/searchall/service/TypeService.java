package com.wh.searchall.service;

import com.wh.searchall.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/14 14:58
 **/
public interface TypeService {
    List<Type> getAllType();

    Type getTypeByName(String name);

    int saveType(Type type);

    Type getType(Long id);

    int updateType(Type type);

    int deleteType(Long id);
}
