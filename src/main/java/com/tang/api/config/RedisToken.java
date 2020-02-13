package com.tang.api.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.UUID;

/**
 * @Classname RedisToken
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/2/13 15:32
 * @Created by ASUS
 */
@Component
public class RedisToken {

    @Resource
    RedisTemplate<String, String> redisTemplate;

    public String getToken() {

        String token = "token:" + UUID.randomUUID().toString().replaceAll("-", "");

        redisTemplate.opsForValue().set(token, token);

        return token;
    }

    public Boolean findToken(String token) {

        Optional<String> stringOptional = Optional.ofNullable(redisTemplate.opsForValue().get(token));

// 1.token 存在则删除
        stringOptional.ifPresent((tokenValue) -> {
            redisTemplate.delete(token);
        });
// 2. token 是否存在
        return stringOptional.isPresent();

    }



}