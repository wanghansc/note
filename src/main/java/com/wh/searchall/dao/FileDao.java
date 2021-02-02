package com.wh.searchall.dao;

import com.wh.searchall.pojo.File;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/2/1 16:28
 **/
@Mapper
public interface FileDao {
    int addFile(File file);

    List<File> getAllType();

    int deleteById(Long id);
}
