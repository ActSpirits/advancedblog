package com.example.demo.config;

import com.example.demo.interceptor.AdminInterceptor;
import com.example.demo.interceptor.CrossOriginInterceptor;
import com.example.demo.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CrossOriginInterceptor()).addPathPatterns("/**").excludePathPatterns("/","/css/**","/fonts/**","/img/**","/js/**").order(1);
        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/user/getLoginUser","/comment/insertComment","/blog/increaseLikeById").order(2);
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**").order(3);
    }
}
