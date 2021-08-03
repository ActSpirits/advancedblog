package com.example.demo.service;

import com.example.demo.bean.Comment;

import java.util.Date;
import java.util.List;

public interface CommentService {
    public List<Comment> listCommentByBlogId(Integer blogId);

    public void insertCommentWithoutParentId(String content, Date date, Integer blogId, Integer id);

    public void insertCommentWithParentId(String content, Date date, Integer blogId, Integer id,Integer parentId);
}
