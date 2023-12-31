package com.lagou.edu.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈〉
 *
 * @author 商玉
 * @create 2022/1/19
 * @since 1.0.0
 */
@RestController
@RequestMapping("/config/")
@RefreshScope
public class ConfigController {

    // 和取本地配置信息一样
    @Value("${lagou.message}")
    private String lagouMessage;
    @Value("${mysql.url}")
    private String mysqlUrl;


    // 内存级别的配置信息
    // 数据库，redis配置信息

    @GetMapping("/viewconfig")
    public String viewconfig() {
        return "lagouMessage==>" + lagouMessage  + " mysqlUrl=>" + mysqlUrl;
    }
}
