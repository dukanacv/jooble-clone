package com.example.joobleclone.controllers;

import com.example.joobleclone.dtos.LoginDto;
import com.example.joobleclone.models.AppUser;
import com.example.joobleclone.security.CustomUserDetailsService;
import com.example.joobleclone.services.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;
    private final CustomUserDetailsService customUserDetailsService;

    public AppUserController(AppUserService appUserService, CustomUserDetailsService customUserDetailsService) {
        this.appUserService = appUserService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> register(@RequestBody AppUser appUser){
        AppUser userToRegister = appUserService.register(appUser);
        return new ResponseEntity<>(userToRegister, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDetails> login(@AuthenticationPrincipal UserDetails userDetails){
//        AppUser appUser = appUserService.login(loginDto.getUsername(), loginDto.getPassword());
        UserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userDetails.getUsername());
        if(customUserDetails != null){
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
