package com.wh.searchall.web;

import com.wh.searchall.service.FileService;
import com.wh.searchall.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/2/2 17:37
 **/
@Controller
public class FileDownLoadController {


    @Value("${spring.data.filePath}")
    private String path;

    @GetMapping("/file/download/{fileName}")
    public void download(HttpServletResponse response, @PathVariable String fileName) throws UnsupportedEncodingException {
        String fileContentType = FileServiceImpl.getFileContentType(fileName);
        response.setContentType(fileContentType+";charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "inline;fileName=" + java.net.URLEncoder.encode(fileName,"UTF-8"));
        java.io.File file = new java.io.File(path+java.io.File.separator+fileName);
        OutputStream os = null;
        try {
            // 文件输入流
            InputStream is = new FileInputStream(file);
            // 相应输出流
            os = response.getOutputStream();
            byte[] buffer = new byte[1024]; // 文件流缓存池
            while (is.read(buffer) != -1) {
                os.write(buffer);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
