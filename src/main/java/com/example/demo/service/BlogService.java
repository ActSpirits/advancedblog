package com.example.demo.service;

import com.example.demo.bean.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BlogService {

    public List<Blog> listBlog();

    public Blog getOneById(Integer id);

    public List<Blog> listBlogByViewLimited();

    public List<Blog> listBlogByTagName(String tagName);


    public void insertBlog(String title, String description, String content, String picture, int i, int i1, Date date, Integer tagId);

    public void deleteBlogById(Integer id);

    public void updateBlogById(Integer id, String title, String description, String content, String picture, Integer tagId);

    public void incrementViewByBlogId(Integer id);
}
