package com.example.demo.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("AdminInterceptor");
        Map<String,Object> map = new HashMap<>();

        //获取请求头中的令牌
        String token = request.getHeader("token");
        System.out.println(token);
        try {
            DecodedJWT verify = JWTUtils.verify(token);
            System.out.println(verify.getClaims().get("type").asString());
            boolean type = verify.getClaims().get("type").asString().equals("1");
            if (type == true){
                return true;
            }else {
                map.put("message","用户权限不够!");
                map.put("status",false);
                String json = new ObjectMapper().writeValueAsString(map);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(json);
                return false;
            }

        }catch (SignatureVerificationException e){
            map.put("message","无效签名!");
        }catch (TokenExpiredException e){
            map.put("message","token过期!");
        }catch (AlgorithmMismatchException e){
            map.put("message","算法不匹配!");
        }catch (Exception e){
            map.put("message","token无效!");
        }
        map.put("status",false);
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
