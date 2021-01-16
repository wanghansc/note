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
}
