package com.lagou.service;

import com.lagou.service.callback.ResumeFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 〈服务调用接口〉
 *
 * @author 商玉
 * @create 2022/1/12
 * @since 1.0.0
 */
@FeignClient(name = "lagou-service-resume",fallback = ResumeFallBack.class,path = "/resume")
public interface ResumeFeignClient {

    @RequestMapping(value = "/openstate/{userId}", method = RequestMethod.GET)
    //@RequestMapping(value = "/resume/openstate/{userId}", method = RequestMethod.GET)
    public Integer findResumeOpenState(@PathVariable(value = "userId") Long userId);
}
