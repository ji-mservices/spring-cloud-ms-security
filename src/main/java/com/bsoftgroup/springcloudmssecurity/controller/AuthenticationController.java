package com.bsoftgroup.springcloudmssecurity.controller;

import com.bsoftgroup.springcloudmssecurity.bean.AuthenticationRequest;
import com.bsoftgroup.springcloudmssecurity.service.UserService;
import com.bsoftgroup.springcloudmssecurity.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    //REVIEW! add post method to create new user (with password encrypted)

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticated(@RequestBody AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        final UserDetails user = userService.getUserDetailByUsername(request.username());

        if (user != null) {
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }

        return ResponseEntity.status(400).body("Some error has occurred");
    }

}
