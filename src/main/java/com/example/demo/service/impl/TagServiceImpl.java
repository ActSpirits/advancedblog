package com.example.demo.service.impl;

import com.example.demo.bean.Tag;
import com.example.demo.mapper.BlogMapper;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.TagMapper;
import com.example.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Tag> listTag() {
        List<Tag> tags = tagMapper.listTag();
        tags.sort(new Comparator<Tag>() {
            @Override
            public int compare(Tag o1, Tag o2) {
                return Integer.compare(o2.getBlogNumber(),o1.getBlogNumber());
            }
        });
        return tags;
    }

    @Override
    public void deleteTagById(Integer id) {
        commentMapper.deleteCommentByTagId(id);
        blogMapper.deleteBlogByTagId(id);
        tagMapper.deleteTagById(id);
    }

    @Override
    public void insertOne(String name) {
        tagMapper.insertOne(name);
    }

    @Override
    public List<String> listTagName() {
        return tagMapper.listTagName();
    }

    @Override
    public void updateTagName(Integer id, String name) {
        tagMapper.updateTagName(id,name);
    }
}
