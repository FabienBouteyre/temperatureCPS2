package com.example.temperature.Service;


import com.example.temperature.Entity.Roles;
import com.example.temperature.Entity.User;
import com.example.temperature.Repository.RepUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;

@Service
public class ServiceUserDetails implements UserDetailsService {

    @Autowired
    private RepUser repUser;

    public boolean addUser(User user){
        User user1 = repUser.findByEmail(user.getEmail());
        if (!StringUtils.isEmpty(user1))
            return false;

        user.setActive(true);
        user.setRoles(Collections.singleton(Roles.USER));
        repUser.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repUser.findByEmail(username);
    }
}
