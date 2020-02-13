package com.tang.api.service;

import com.tang.api.config.RedisToken;
import com.tang.api.domain.Orders;
import com.tang.api.mapper.OrdersMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Classname ApiService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/2/13 14:58
 * @Created by ASUS
 */
@Service
public class ApiService {

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private RedisToken redisToken;

    public String getToken() {
        return redisToken.getToken();
    }

    public String addOrder(Orders orders) {

        return ordersMapper.insert(orders) > 0 ? "插入成功!" : "插入失败!";

    }
}
