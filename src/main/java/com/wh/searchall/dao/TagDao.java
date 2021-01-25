package com.wh.searchall.dao;

import com.wh.searchall.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/14 21:52
 **/
@Mapper
public interface TagDao {
    List<Tag> getAllTag();

    Tag getTagByName(String name);

    int saveTag(Tag tag);

    Tag getTag(Long id);

    int updateTag(Tag tag);

    int deleteTag(Long id);

    List<Tag> findAll(List<Long> list);

    List<Tag> getBlogTag();

}
