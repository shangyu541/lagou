package com.lagou.edu.dao;

import com.lagou.edu.pojo.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 〈〉
 *
 * @author 商玉
 * @create 2022/1/10
 * @since 1.0.0
 */
public interface ResumeDao extends JpaRepository<Resume,Long> {

}
