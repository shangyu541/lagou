package com.lagou.edu.service;

import com.lagou.edu.pojo.Resume;

/**
 * 〈〉
 *
 * @author 商玉
 * @create 2022/1/10
 * @since 1.0.0
 */
public interface ResumeService {

    Resume findDefaultResumeByUserId(Long userId);

}
