package com.wh.searchall.service.impl;

import com.wh.searchall.dao.TagDao;
import com.wh.searchall.dao.TypeDao;
import com.wh.searchall.pojo.Tag;
import com.wh.searchall.pojo.Type;
import com.wh.searchall.service.TagService;
import com.wh.searchall.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/1/14 20:25
 **/
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;
    @Override
    public List<Tag> getAllTag() {
        return tagDao.getAllTag();
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagDao.saveTag(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagDao.getTag(id);
    }

    @Transactional
    @Override
    public int updateTag(Tag tag) {
        return tagDao.updateTag(tag);
    }

    @Transactional
    @Override
    public int deleteTag(Long id) {
        return tagDao.deleteTag(id);
    }

    @Override
    public List<Tag> listTag(String tagIds) {
        return tagDao.findAll(convertToList(tagIds));
    }

    private List<Long> convertToList(String tagIds) {
        List<Long> list = new ArrayList<>();
        if (null!=tagIds&&!"".equals(tagIds.trim())) {
            String[] array = tagIds.split(",");
//            list = Arrays.asList(array);
            for (int i=0; i < array.length;i++) {
                list.add(new Long(array[i]));
            }
        }
        return list;
    }
}
