package com.lagou.log;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈〉
 *
 * @author 商玉
 * @create 2022/1/12
 * @since 1.0.0
 */
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLevel(){
        return Logger.Level.FULL;
    }
}
