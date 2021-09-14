package com.example.demo.controller;

import com.example.demo.bean.Blog;
import com.example.demo.bean.User;
import com.example.demo.service.BlogService;
import com.example.demo.utils.UploadUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/admin/blog")
public class AdminBlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/uploadPicture")
    @CrossOrigin
    public String uploadPic(@RequestParam("picture") MultipartFile picture,
                            HttpServletRequest request) throws IOException {

        return UploadUtils.uploadSinglePic(picture, "blogPicture");
    }

    @RequestMapping("/write")
    @CrossOrigin
    public String write(@RequestBody Map<String, String> requestBody) {
        String title = requestBody.get("title");
        String description = requestBody.get("description");
        String content = requestBody.get("content");
        String picture = requestBody.get("picture");
        Integer tagId = Integer.valueOf(requestBody.get("tagId"));
        blogService.insertBlog(title, description, content, picture, 0, 0, new Date(), tagId);
        return "写作成功!";
    }

    @RequestMapping("/writeOrUpdate")
    @CrossOrigin
    public String writeOrUpdate(@RequestBody Map<String, String> requestBody) {
        String id = requestBody.get("id");
        System.out.println(id);
        String title = requestBody.get("title");
        String description = requestBody.get("description");
        String content = requestBody.get("content");
        String picture = requestBody.get("picture");
        Integer tagId = Integer.valueOf(requestBody.get("tagId"));
        System.out.println(title);
        System.out.println(description);
        System.out.println(content);
        System.out.println(picture);
        System.out.println(tagId);
        if (id != null && !"".equals(id)){
            Integer id1 = Integer.valueOf(id);
            System.out.println("修改");
            blogService.updateBlogById(id1, title, description, content, picture, tagId);
            return "修改博客成功!";
        }else {
            System.out.println("新建");
            blogService.insertBlog(title, description, content, picture, 0, 0, new Date(), tagId);
            return "写作成功!";
        }
    }

    @RequestMapping("/getBlogById")
    @CrossOrigin
    public Blog getBlogById(@RequestParam("id") Integer id) {
        return blogService.getOneById(id);
    }

    @RequestMapping("/listBlog")
    @CrossOrigin
    public PageInfo<Blog> listBlog(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
                                   @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        PageHelper.startPage(pn, pageSize);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogService.listBlog());
        return pageInfo;
    }

    @RequestMapping("/deleteBlogById")
    @CrossOrigin
    public String deleteBlogById(@RequestParam("id") Integer id) {
        blogService.deleteBlogById(id);
        return "删除成功!";
    }

}
