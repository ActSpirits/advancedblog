package com.example.demo.service.impl;


import com.example.demo.bean.Blog;
import com.example.demo.bean.Statistical;
import com.example.demo.mapper.BlogMapper;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.service.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticalServiceImpl implements StatisticalService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Statistical get() {
        Statistical statistical = new Statistical();
        statistical.setBlogNumber(blogMapper.countBlog());
        statistical.setTotalView(blogMapper.countTotalView());
        statistical.setCommentNumber(commentMapper.countComment());
        return statistical;
    }
}
