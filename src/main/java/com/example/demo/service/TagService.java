package com.example.demo.service;

import com.example.demo.bean.Tag;

import java.util.List;

public interface TagService {

    public List<Tag> listTag();

    public void deleteTagById(Integer id);

    public void insertOne(String name);

    public List<String> listTagName();

    public void updateTagName(Integer id, String name);
}
