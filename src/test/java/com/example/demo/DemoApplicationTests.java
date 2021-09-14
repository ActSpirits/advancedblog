package com.example.demo;

import com.example.demo.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BlogService blogService;

    @Test
    void contextLoads() {
        System.out.println(redisTemplate.execute(RedisConnectionCommands::ping));
//        System.out.println(blogService.listBlog());
    }


}
