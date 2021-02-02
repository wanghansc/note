package com.wh.searchall.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wh.searchall.pojo.File;
import com.wh.searchall.pojo.User;
import com.wh.searchall.service.FileService;
import com.wh.searchall.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

/**
 * @author wanghan
 * @description TODO
 * @date 2021/2/1 14:55
 **/
@Controller
@RequestMapping("/admin")
public class FileController {
    @Autowired
    private FileService fileService;

    @Value("${spring.data.filePath}")
    private String path;

    @PostMapping("/files")
    public String file(@RequestParam(required = false,value = "file")MultipartFile file, RedirectAttributes attributes, HttpSession session) {
        User user = (User) session.getAttribute("user");
        File file1 = fileService.addFile(file, user);
//        if (file1 != null) {
//            result.rejectValue("name","nameError","不能添加重复的标签");
//        }
//        if (result.hasErrors()) {
//            return "admin/tags-input";
//        }
        if (file1 != null ) {
            attributes.addFlashAttribute("message", "新增成功");
        } else {
            attributes.addFlashAttribute("message", "新增失败");
        }
        return "redirect:/admin/files";
    }

    @GetMapping("/file/input")
    public String files(Model model){
        model.addAttribute("file", new File());
        return "admin/upload-file";
    }

    @GetMapping("/files")
    public String files(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum, Model model){
        PageHelper.startPage(pageNum, 5);
        List<File> allFile = fileService.getAllType();
        //分页结果
        PageInfo<File> pageInfo = new PageInfo<>(allFile);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/files";
    }

    @GetMapping("/file/{fileName}/download")
    public void download(HttpServletResponse response, @PathVariable String fileName) throws UnsupportedEncodingException {
        String fileContentType = FileServiceImpl.getFileContentType(fileName);
        response.setContentType(fileContentType+";charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName,"UTF-8"));
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

    @GetMapping("/file/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        int b = fileService.deleteById(id);
        if (b == 0 ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/files";
    }
}
