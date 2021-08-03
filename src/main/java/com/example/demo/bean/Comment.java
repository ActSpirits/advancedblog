package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer id;
    private String content;
    private Date time;
    private Blog blog;
    private User user;
    private Comment parentComment;
    private List<Comment> commentList;

    public String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH-MM-ss").format(time);
    }
}
