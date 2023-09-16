package com.lagou.config;

import com.lagou.service.JdbcUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

/**
 * 〈〉
 *
 * @author 商玉
 * @create 2022/1/24
 * @since 1.0.0
 */
@Configuration
public class SecurityConfiger extends WebSecurityConfigurerAdapter {

    /**
     * 注册一个认证管理器对象到容器
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JdbcUserDetailsService jdbcUserDetailsService;

    /**
     * 处理用户名和密码验证事宜
     * 1） 客户端传递username和password参数到认证服务器
     * 2） 一般来说，username和password会存储再数据库中的用户表中
     * 3） 根据用户表中数据，验证当前传递过来的用户信息的合法性
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //User admin = new User("admin", "123456", new ArrayList<>());
        //auth.inMemoryAuthentication().withUser(admin).passwordEncoder(passwordEncoder);
        auth.userDetailsService(jdbcUserDetailsService).passwordEncoder(passwordEncoder);
    }
}
