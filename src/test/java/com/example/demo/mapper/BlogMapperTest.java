package com.example.demo.mapper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.bean.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Calendar;

@SpringBootTest
class BlogMapperTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void insertBlog() {
    }

    /**
     * 令牌的获取
     *
     */
    @Test
    public void getJWT() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 1000);
        String sign = JWT.create()
                .withClaim("username", "ActSpirits")
                .withClaim("userId", 1)
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256("Scwm,dnyx0"));
        System.out.println(sign);
    }

    /**
     * eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Mjc1NDA3NzAsInVzZXJuYW1lIjoiQWN0U3Bpcml0cyJ9.BxCpRHcnnR9YBARAwhZ7ySDJe6DODFOhV1DY2v_lToc
    */

    /**
     * 令牌的验签
     */
    @Test
    public void testJWT() {
        //创建验证对象
//        JWTVerifier build = JWT.require(Algorithm.HMAC256("Scwm,dnyx0")).build();
//        DecodedJWT verify = build.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Mjc1NDI0ODQsInVzZXJJZCI6MSwidXNlcm5hbWUiOiJBY3RTcGlyaXRzIn0._oDNryjKjxPxapeYPWmIus6IBgiwXSnG0uqCINGc8Eo");
//        System.out.println(verify.getClaims().get("username").asString());
//        System.out.println(verify.getClaims().get("userId").asInt());
    }

    @Test
    public void testRedis(){
//        redisTemplate.opsForValue().set("user","zzj");
//        System.out.println(redisTemplate.opsForValue().get("user"));
//        Boolean user = redisTemplate.delete("user");
//        System.out.println(user);
    }
}