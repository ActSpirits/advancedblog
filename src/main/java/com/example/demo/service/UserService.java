package com.example.demo.service;

import com.example.demo.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserService {
    public User getUserByUsername(String username);

    public void insertOne(String username, String password, String email, String picture, Date date, int i);

    public List<String> listAllEmail();

    public User getUserByEmail(String email);
}
