package com.lagou;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * 〈〉
 *
 * @author 商玉
 * @create 2022/1/27
 * @since 1.0.0
 */
public class SentinelFallbackClass {


    public static Integer handlerException(Long userId, BlockException block) {
        return -100;
    }

    public static Integer handlerError(Long userId, BlockException block) {
        return -500;
    }


}
