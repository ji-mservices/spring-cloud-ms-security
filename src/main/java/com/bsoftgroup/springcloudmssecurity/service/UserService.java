package com.bsoftgroup.springcloudmssecurity.service;

import com.bsoftgroup.springcloudmssecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDetails getUserDetailByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(x -> new User(x.getUsername(), x.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .orElseThrow(() -> new UsernameNotFoundException("No user was found"));
    }

}
