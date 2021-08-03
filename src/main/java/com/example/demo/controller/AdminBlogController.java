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

    @RequestMapping("/updateBlogById")
    @CrossOrigin
    public String updateBlogById(@RequestBody Map<String, String> requestBody) {
        Integer id = Integer.valueOf(requestBody.get("id"));
        String title = requestBody.get("title");
        String description = requestBody.get("description");
        String content = requestBody.get("content");
        String picture = requestBody.get("picture");
        Integer tagId = Integer.valueOf(requestBody.get("tagId"));
        blogService.updateBlogById(id, title, description, content, picture, tagId);
        return "修改博客成功!";
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
