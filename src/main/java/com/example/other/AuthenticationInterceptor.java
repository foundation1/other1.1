package com.example.other;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.other.entity.dto.ReturnMessageDto;
import com.example.other.entity.UserEntity;
import com.example.other.entity.dto.Error;
import com.example.other.service.UserService;
import com.example.other.config.jwt.PassToken;
import com.example.other.config.jwt.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Resource
    private UserService UserService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    returnJson(httpServletResponse);
                    return false;
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    returnJson(httpServletResponse);
                    return false;
                }
                UserEntity user = UserService.getById(userId);
                if (user == null) {
                    returnJson(httpServletResponse);
                    return false;
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPwd())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    returnJson(httpServletResponse);
                    return false;
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }


    private void returnJson(HttpServletResponse response) throws Exception {
        ReturnMessageDto returnMessageDto = new ReturnMessageDto(Error.INFO_203);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSONUtil.toJsonStr(returnMessageDto));

        } catch (IOException e) {
            log.info("response error", e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
