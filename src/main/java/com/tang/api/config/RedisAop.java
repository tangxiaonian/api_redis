package com.tang.api.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * @Classname RedisAop
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/2/13 15:57
 * @Created by ASUS
 */
@Aspect
@Component
public class RedisAop {

    @Resource
    private RedisToken redisToken;

    @Pointcut(value = "execution(public * com.tang.api.controller..*.*(..))")
    public void executionMethod() {}

    /**
     * 前置通知: 拦截方法执行前 判断是否需要生成 token
     * @param joinPoint
     */
    @Before(value = "executionMethod()")
    public void beforeMethod(JoinPoint joinPoint) {

        // 得到方法的信息
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();

        // 1.判断方法是否标注 TokenAnnotation 注解
        Optional<Annotation> annotationOptional = Optional.ofNullable(methodSignature.getMethod().getDeclaredAnnotation(TokenAnnotation.class));

        // 2.若标注了  生成一次性token  写到请求中
        annotationOptional.ifPresent((annotation -> {

            String token = redisToken.getToken();

            getRequest().setAttribute("token",token);

        }));

    }

    @Around(value = "executionMethod()")
    public Object aroundMethod(JoinPoint joinPoint) throws IOException {

        // 1.得到方法的信息
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();

        // 2.判断方法是否标注 ApiAnnotation 注解
        Optional<Annotation> optionalAnnotation = Optional.ofNullable(
                methodSignature.getMethod().getDeclaredAnnotation(ApiAnnotation.class));

        // 3.ApiAnnotation 存在 验证token的有效性
        if (optionalAnnotation.isPresent()) {

            // 4.获取 ApiAnnotation 注解对象
            ApiAnnotation apiAnnotation = (ApiAnnotation)optionalAnnotation.get();
            // 5.获取 type 值
            String type = apiAnnotation.type();

            String token = "";

            if (!"".equals(type)) {

                // 6.获取 token
                if (ConstUtils.header.equals(type)) {

                    token = getRequest().getHeader("token");

                }else {

                    token = getRequest().getParameter("token");
                }

                System.out.println("token：---》" + token );

                // 7.验证token, token 不存在,说明 表单重复提交了
                if (!redisToken.findToken(token)) {

                    response("表单重复提交了!");

                    // 业务方法不执行,直接返回
                    return null;
                }

            }
        }

        // 8.执行业务方法
        ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint) joinPoint;

        try {
            return proceedingJoinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

    private HttpServletRequest getRequest() {

        // RequestContextHolder 获取当前的请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        return request;
    }

    private void response(String msg) throws IOException {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletResponse response = attributes.getResponse();

        response.setHeader("Content-type", "text/html;charset=UTF-8");

        PrintWriter writer = response.getWriter();

        try {
            writer.println(msg);
        } catch (Exception e) {

        } finally {
            writer.close();
        }

    }

}