package com.wh.searchall.service;

import com.wh.searchall.pojo.Tag;
import com.wh.searchall.pojo.Type;

import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/14 14:58
 **/
public interface TagService {
    List<Tag> getAllTag();

    Tag getTagByName(String name);

    int saveTag(Tag tag);

    Tag getTag(Long id);

    int updateTag(Tag tag);

    int deleteTag(Long id);

    List<Tag> listTag(String tagIds);
}
