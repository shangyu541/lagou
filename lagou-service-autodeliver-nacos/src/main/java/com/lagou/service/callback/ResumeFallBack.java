package com.lagou.service.callback;

import com.lagou.service.ResumeFeignClient;
import org.springframework.stereotype.Component;

/**
 * 〈〉
 *
 * @author 商玉
 * @create 2022/1/12
 * @since 1.0.0
 */
@Component
public class ResumeFallBack implements ResumeFeignClient {

    @Override
    public Integer findResumeOpenState(Long userId) {
        return -1;
    }
}
