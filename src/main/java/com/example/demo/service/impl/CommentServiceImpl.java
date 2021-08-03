package com.example.demo.service.impl;

import com.example.demo.bean.Comment;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> listCommentByBlogId(Integer blogId) {
        //找出所有没有父亲的评论
        List<Comment> commentList = commentMapper.listCommentWithoutNoParentByBlogId(blogId);
        //新建一个评论集合对最终评论集合进行封装，遍历所有没有父亲的评论，找出他们的子评论commentList
        List<Comment> finalCommentList = new ArrayList<>();
        for (Comment comment:commentList){
//            comment.setCommentList();
            comment.setCommentList(listAllSonCommentByParent(comment));
            finalCommentList.add(comment);
        }
        return finalCommentList;
    }

    @Override
    public void insertCommentWithoutParentId(String content, Date date, Integer blogId, Integer id) {
        commentMapper.insertCommentWithoutParentId(content,date,blogId,id);
    }

    @Override
    public void insertCommentWithParentId(String content, Date date, Integer blogId, Integer id,Integer parentId) {
        commentMapper.insertCommentWithParentId(content,date,blogId,id,parentId);
    }

    public List<Comment> listAllSonCommentByParent(Comment comment){
        List<Comment> finalCommentList = new ArrayList<>();
        List<Comment> commentList = commentMapper.listRelatedSonCommentByParentId(comment.getId());
        for (Comment comment1:commentList){
            finalCommentList.add(comment1);
            finalCommentList.addAll(listAllSonCommentByParent(comment1));
        }
        return finalCommentList;
    }

}
