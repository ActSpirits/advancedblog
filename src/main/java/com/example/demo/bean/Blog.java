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
public class Blog {
    private Integer id;
    private String title;
    private String description;
    private String content;
    private String picture;
    private Integer view;
    private Integer like;
    private Date time;
    private Tag tag;
    private Integer commentNumber;
    private List<Comment> commentList;

    public Blog(String title, String description, String content, String picture, Integer view, Integer like, Date time) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.picture = picture;
        this.view = view;
        this.like = like;
        this.time = time;
    }

    public String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }
}
