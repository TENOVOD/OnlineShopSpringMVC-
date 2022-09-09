package com.stapelok.stapelok.security;


import com.stapelok.stapelok.config.SecurityConfig;
import com.stapelok.stapelok.models.LoginStat;
import com.stapelok.stapelok.models.User;
import com.stapelok.stapelok.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        //user.setStatus();
        System.out.println(user);
        return  SecurityUser.fromUser(user);
    }
}
