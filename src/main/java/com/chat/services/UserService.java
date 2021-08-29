package com.chat.services;

import java.util.Collections;
import java.util.List;

import com.chat.domain.User;
import com.chat.repository.UserDetailsRepository;
import org.apache.tomcat.util.json.JSONParser;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = user.getId();
        return userDetailsRepository.findById(userId).orElse(user);
    }

    public void changeUsername(String newUsername) {
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userDetails.setUsername(newUsername);
        userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDetailsRepository.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
