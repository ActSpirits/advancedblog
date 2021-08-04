package com.example.demo.service.impl;

import com.example.demo.bean.Blog;
import com.example.demo.mapper.BlogMapper;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public List<Blog> listBlog() {
        return blogMapper.listBlog();
    }

    @Override
    public Blog getOneById(Integer id) {
        return blogMapper.getOneById(id);
    }

    @Override
    public List<Blog> listBlogByViewLimited() {
        return blogMapper.listBlogByViewLimited();
    }

    @Override
    public List<Blog> listBlogByTagName(String tagName) {
        return blogMapper.listBlogByTagName(tagName);
    }

    @Override
    public void insertBlog(String title, String description, String content, String picture, int i, int i1, Date date, Integer tagId) {
        blogMapper.insertBlog(title,description,content,picture,i,i1,date,tagId);
    }

    @Override
    public void deleteBlogById(Integer id) {
        commentMapper.deleteCommentByBlogId(id);
        blogMapper.deleteBlogById(id);
    }

    @Override
    public void updateBlogById(Integer id, String title, String description, String content, String picture, Integer tagId) {
        blogMapper.updateBlogById(id,title,description,content,picture,tagId);
    }

    @Override
    public void incrementViewByBlogId(Integer id) {
        blogMapper.incrementViewByBlogId(id);
    }

    @Override
    public List<Blog> listBlogLikeTitle(String content) {
        return blogMapper.listBlogLikeTitle(content);
    }

    @Override
    public void updateLikeById(Integer id, Integer like) {
        blogMapper.updateLikeById(id,like);
    }

    @Override
    public Integer getLikeById(Integer id) {
        return blogMapper.getLikeById(id);
    }


}
