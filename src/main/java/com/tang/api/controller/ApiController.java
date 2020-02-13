package com.tang.api.controller;

import com.tang.api.config.ApiAnnotation;
import com.tang.api.config.ConstUtils;
import com.tang.api.config.TokenAnnotation;
import com.tang.api.domain.Orders;
import com.tang.api.service.ApiService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @Classname ApiController
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/2/13 14:57
 * @Created by ASUS
 */
@Controller
public class ApiController {

    @Resource
    private ApiService apiService;

    @TokenAnnotation
    @GetMapping("/indexPage")
    public String getToken() {
        return "index";
    }

    @ApiAnnotation(type = ConstUtils.form)
    @PostMapping("/add")
    @ResponseBody
    public String addOrder(Orders orders) {
        return apiService.addOrder(orders);
    }

}