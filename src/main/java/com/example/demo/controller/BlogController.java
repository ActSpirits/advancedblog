package com.example.demo.controller;


import com.example.demo.bean.Blog;
import com.example.demo.service.BlogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/listBlog")
    @CrossOrigin
    public PageInfo<Blog> listBlog(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        PageHelper.startPage(pn, pageSize);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogService.listBlog());
        return pageInfo;
    }

    @RequestMapping("/getBlogById")
    @CrossOrigin
    public Blog getBlogById(@RequestParam("id") Integer id) {
        Blog oneById = blogService.getOneById(id);
        Integer like =(Integer) redisTemplate.opsForHash().get("blog", id);
        if (like == null){
            like = 0;
        }
        oneById.setLike(like);
        blogService.incrementViewByBlogId(id);
        return oneById;
    }

    @RequestMapping("/listBlogByViewLimited")
    @CrossOrigin
    public List<Blog> listBlogByViewLimited() {
        return blogService.listBlogByViewLimited();
    }

    @RequestMapping("/listBlogByTagName")
    @CrossOrigin
    public PageInfo<Blog> listBlogByTag(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                        @RequestParam(value = "tagName", required = false) String tagName) {
        PageHelper.startPage(pn, pageSize);
        if (tagName == null || "".equals(tagName)) {
            PageInfo<Blog> pageInfo = new PageInfo<>(blogService.listBlog());
            return pageInfo;
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(blogService.listBlogByTagName(tagName));
        return pageInfo;
    }

    @RequestMapping("/increaseLikeById")
    @CrossOrigin
    public Map<String,Object> increaseLikeById(@RequestBody Map<String,String> map1){
        Map<String,Object> map = new HashMap<>();
        String param = map1.get("param");
        Integer id = Integer.valueOf(param);
        redisTemplate.opsForHash().increment("blog",id,1);
        map.put("message","点赞成功!");
        map.put("like",redisTemplate.opsForHash().get("blog",id));
        return map;
    }
}
