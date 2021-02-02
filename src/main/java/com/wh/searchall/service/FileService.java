package com.wh.searchall.service;

import com.wh.searchall.pojo.File;
import com.wh.searchall.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/2/1 15:10
 **/
public interface FileService {

    File addFile(MultipartFile file, User user);

    List<File> getAllType();

    int deleteById(Long id);

    int deleteByBlogId(Long id);

    int importAll();

    int deleteAll();
}
