package com.example.demo;

import com.example.demo.bean.Blog;
import com.example.demo.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class DemoApplication {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BlogService blogService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Scheduled(cron = "0 0 0 * * ?") //每天0点刷新
//    @Scheduled(cron = "* * * * * ?") //每s更新
    public void update(){
        log.info("定时任务");
        List<Blog> blogList = blogService.listBlog();
        for (Blog blog :blogList){
            Integer like =(Integer) redisTemplate.opsForHash().get("blog", blog.getId());
            if (like == null){
                blogService.updateLikeById(blog.getId(),0);
            }else {
                blogService.updateLikeById(blog.getId(),like);
            }
        }
        redisTemplate.delete("blog");
        for (Blog blog:blogList){
            Integer like = blogService.getLikeById(blog.getId());
            redisTemplate.opsForHash().put("blog",blog.getId(),like);
        }
    }
}
