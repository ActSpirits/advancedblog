package com.example.demo.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.bean.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JWTUtils;
import com.example.demo.utils.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    @CrossOrigin
    public Map<String, Object> login(@RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        User userByUsername = userService.getUserByUsername(username);
        if (userByUsername == null) {
            map.put("message", "用户名不存在!");
            return map;
        } else if (!userByUsername.getPassword().equals(password)) {
            map.put("message", "密码错误!");
            return map;
        } else {
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("id", userByUsername.getId().toString());
            tokenMap.put("username", userByUsername.getUsername());
            tokenMap.put("email", userByUsername.getEmail());
            tokenMap.put("picture", userByUsername.getPicture());
            tokenMap.put("time", userByUsername.getTime().toString());
            tokenMap.put("type", userByUsername.getType().toString());
            String token = JWTUtils.getToken(tokenMap);

            map.put("message", "登录成功!");
            map.put("token", token);
            return map;
        }
    }

    @RequestMapping("/getLoginUser")
    @CrossOrigin
    public Map<String, Object> getLoginUser(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        map.put("id", verify.getClaims().get("id").asString());
        map.put("username", verify.getClaims().get("username").asString());
        map.put("email", verify.getClaims().get("email").asString());
        map.put("picture", verify.getClaims().get("picture").asString());
        map.put("time", verify.getClaims().get("time").asString());
        map.put("type", verify.getClaims().get("type").asString());
        map.put("message", "用户已登录!");
        return map;
    }

    @RequestMapping("/sendEmailCode")
    @CrossOrigin
    public String sendEmailCode(@RequestParam("email") String email,
                                HttpServletRequest request) {
        List<String> stringList = userService.listAllEmail();
        if (stringList.contains(email)) {
            return "该邮箱已被注册!";
        }
        String emailCode = UUID.randomUUID().toString().substring(0, 5).toLowerCase();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("注册验证码");
        simpleMailMessage.setText("注册验证码是：" + emailCode);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom("1697279908@qq.com");
        try {
            javaMailSender.send(simpleMailMessage);
            UUID uuid = UUID.randomUUID();
            redisTemplate.opsForValue().set("emailCode" + ":" + email, emailCode, 60, TimeUnit.SECONDS);
            return "发送成功!";
        } catch (MailSendException e) {
            return "发送不成功,邮箱不存在!";
        }
    }

    @RequestMapping("/register")
    @CrossOrigin
    public Map<String,Object> register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("email") String email,
                           @RequestParam("emailCode") String emailCode,
                           @RequestParam("picture") String picture,
                           HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
//        log.info(request.getSession().getId());
        if (username.length() >= 14 || username.length() < 2) {
            map.put("message","用户名不能太短或过长!");
            return map;
        } else if (password.length() >= 16 || password.length() < 6) {
            map.put("message","密码不能太短或太长!");
            return map;
        }
        User userByUsername = userService.getUserByUsername(username);
        if (userByUsername != null) {
            map.put("message","当前用户名已存在!");
            return map;
        } else if (redisTemplate.opsForValue().get("emailCode" + ":" + email) == null) {
            map.put("message","请先获取邮箱验证码!");
            return map;
        } else if (!redisTemplate.opsForValue().get("emailCode" + ":" + email).equals(emailCode)) {
            map.put("message","邮箱验证码错误!");
            return map;
        } else {
            userService.insertOne(username, password, email, picture, new Date(), 0);
            User userByUsername1 = userService.getUserByUsername(username);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("id", userByUsername1.getId().toString());
            tokenMap.put("username", userByUsername1.getUsername());
            tokenMap.put("email", userByUsername1.getEmail());
            tokenMap.put("picture", userByUsername1.getPicture());
            tokenMap.put("time", userByUsername1.getTime().toString());
            tokenMap.put("type", userByUsername1.getType().toString());
            String token = JWTUtils.getToken(tokenMap);
            map.put("message", "注册成功!");
            map.put("token", token);
            return map;
        }
    }

    @RequestMapping("/exit")
    @CrossOrigin
    public Map<String, Object> exit(HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "已退出当前账号!");
        map.put("token", null);
        return map;
    }

    @PostMapping("/uploadPicture")
    @CrossOrigin
    public String uploadPic(@RequestParam("picture") MultipartFile picture,
                            HttpServletRequest request) throws IOException {
        return UploadUtils.uploadSinglePic(picture, "userPicture");
    }

    @PostMapping("/sendUserToEmail")
    @CrossOrigin
    public Map<String,Object> sendUserToEmail(@RequestBody Map<String,String> map){
        Map<String,Object> map1 = new HashMap<>();
        String email = map.get("email");
        List<String> stringList = userService.listAllEmail();
        if (!stringList.contains(email)) {
            map1.put("message","该邮箱未被用于注册!");
            return map1;
        }
        User user = userService.getUserByEmail(email);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("账号找回");
        simpleMailMessage.setText("username:"+user.getUsername()+"\n"+"password:"+user.getPassword());
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom("1697279908@qq.com");
        try {
            javaMailSender.send(simpleMailMessage);
            map1.put("message","已将账号密码信息发送至邮箱!");
        } catch (MailSendException e) {
            map1.put("message","发送失败!");
        }
        return map1;
    }


}
