package com.wh.searchall.service.impl;

import com.wh.searchall.dao.FileDao;
import com.wh.searchall.pojo.User;
import com.wh.searchall.service.FileService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.wh.searchall.pojo.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/2/1 15:10
 **/
@Service
public class FileServiceImpl implements FileService {

    @Value("${spring.data.filePath}")
    private String path;

    @Value("${spring.data.solr.core1}")
    private String core;

    @Value("${spring.data.solr.core2}")
    private String core2;

    @Autowired
    private HttpSolrClient httpSolrClient;

    @Autowired
    private FileDao fileDao;

    @Override
    public List<File> getAllType() {
        return fileDao.getAllType();
    }

    @Override
    public int deleteAll() {
        try {
            httpSolrClient.deleteByQuery(core2, "*:*");
            httpSolrClient.commit(core);
            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int importAll() {
        SolrQuery query = new SolrQuery();
        //pat核心
        String head = "/" + core2 + "/" + core + "import";
        query.setRequestHandler(head);
        query.setParam("command", "full-import");
        query.setParam("wt", "json");
        try {
            httpSolrClient.query(query);
            //httpSolrClient.commit();
            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteByBlogId(Long id) {
        try {
            httpSolrClient.deleteByQuery(core2, "blog_id:"+id);
            httpSolrClient.commit(core);
//            System.out.println("updateResponse:"+updateResponse);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        int num = fileDao.deleteById(id);
        try {
            httpSolrClient.deleteByQuery(core, "id:f"+id);
            httpSolrClient.commit(core);
//            System.out.println("updateResponse:"+updateResponse);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return num;
    }

    @Override
    public File addFile(MultipartFile file, User user) {
        File myFile = new File();
        //获取文件存储路径
//        String path = "C:\\Users\\wanghan\\Desktop\\file";
        //获取原文件名
        String oldFileName = file.getOriginalFilename();
        System.out.println("文件名："+oldFileName);
        String newFileName = System.currentTimeMillis()+oldFileName;
        //创建文件实例
        java.io.File filePath = new java.io.File(path, newFileName);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录"+filePath);
        }

        //写入文件
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null != user) {
            myFile.setUserId(user.getId());
            myFile.setNickname(user.getNickname());
        }
        myFile.setFileName(newFileName);
        myFile.setFilePath(path);
        fileDao.addFile(myFile);
        //增加索引
        doc(myFile);

        return myFile;
    }
    /**
     * @description //文件索引添加
     * @param f：文件数据库对象
     *         newFileName :文件名
     * @return int
     * @date 2021/2/1 16:52
     */
    public int doc(File f) {
        String path = f.getFilePath();
        String newFileName = f.getFileName();
        ContentStreamUpdateRequest req =  new ContentStreamUpdateRequest("/update/extract");
        java.io.File file = new java.io.File(path, newFileName);
        String contentType = getFileContentType(newFileName);
//        System.out.println(file);
        try {
            req.addFile(file,contentType);
            req.setParam("literal.path", path+ java.io.File.separator+newFileName);
            req.setParam("literal.id","f"+f.getId());
            req.setParam("literal.pathuploaddate", GetCurrentDate());
            req.setParam("literal.blog_title",newFileName);
            req.setParam("literal.blog_user_nickname",f.getNickname());
            req.setParam("literal.blog_user_id",f.getUserId().toString());
            req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
            httpSolrClient.request(req, core);
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * @description //获取当前时间String
     * @return String
     * @date 2021/2/1 16:53
     */
    private static String GetCurrentDate(){
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day =sdf.format(dt);
        return day;
    }

    /**
     * @description //获取文件类型
     * @param filename：文件名
     * @return String：返回类型参数
     * @date 2021/2/1 16:54
     */
    public static String getFileContentType(String filename) {
        String contentType = "";
        String prefix = filename.substring(filename.lastIndexOf(".") + 1);
        if (prefix.equals("xlsx")) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if (prefix.equals("pdf")) {
            contentType = "application/pdf";
        } else if (prefix.equals("doc")) {
            contentType = "application/msword";
        } else if (prefix.equals("txt")) {
            contentType = "text/plain";
        } else if (prefix.equals("xls")) {
            contentType = "application/vnd.ms-excel";
        } else if (prefix.equals("docx")) {
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if (prefix.equals("ppt")) {
            contentType = "application/vnd.ms-powerpoint";
        } else if (prefix.equals("pptx")) {
            contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        }
        else {
            contentType = "othertype";
        }

        return contentType;
    }
}
