package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
@Slf4j
public class BlogPictureConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("blogPictureConfig资源解析器生效");
        String staticPath = System.getProperty("user.dir");
        String rootPath = staticPath.replaceAll("\\\\", "/") + File.separator + "src/main/resources/static/blogPicture/";
        log.info("rootPath:{}", rootPath);

        String finalPath;
        //判断当前系统  根据不同系统选择不同路径
        if (System.getProperty("os.name").contains("Windows")) {
            //若是windows下
            finalPath = "file:/" + rootPath;
        } else {
            //若是linux下
            finalPath = "file:" + rootPath;
        }
        log.info("finalPath:{}", finalPath);
        registry.addResourceHandler("/blogPicture/**").addResourceLocations(finalPath);
    }
}
