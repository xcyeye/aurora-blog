package com.example.webflux.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author qsyyke
 */

@Slf4j
@Component
public class MyDetailsService implements UserDetailsService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //username是用户传过来的

        log.error("MyDetailsService执行");

        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setUsername("aurora");
        myUserDetails.setPassword(passwordEncoder.encode("123456"));
        return myUserDetails;
    }
}
