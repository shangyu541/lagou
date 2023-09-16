package com.lagou.service;

import com.lagou.dao.UsersRepository;
import com.lagou.edu.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 〈〉
 *
 * @author 商玉
 * @create 2022/1/24
 * @since 1.0.0
 */
@Service
public class JdbcUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users byUsername = usersRepository.findByUsername(s);
        System.out.println(byUsername);
        return new User(byUsername.getUsername(),byUsername.getPassword(),new ArrayList<>());
    }

}
