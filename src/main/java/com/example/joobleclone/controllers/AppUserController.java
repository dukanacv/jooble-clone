package com.example.joobleclone.controllers;

import com.example.joobleclone.dtos.LoginDto;
import com.example.joobleclone.models.AppUser;
import com.example.joobleclone.security.CustomUserDetailsService;
import com.example.joobleclone.services.AppUserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@ApiModel(value = "AppUserController", description = "Controller for testing user register and user(company) login methods")
public class AppUserController {

    private final AppUserService appUserService;
    private final CustomUserDetailsService customUserDetailsService;

    public AppUserController(AppUserService appUserService, CustomUserDetailsService customUserDetailsService) {
        this.appUserService = appUserService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    @ApiOperation(value = "Register new app user", notes = "Returns app user model")
    public ResponseEntity<AppUser> register(@RequestBody AppUser appUser){
        AppUser userToRegister = appUserService.register(appUser);
        return new ResponseEntity<>(userToRegister, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login as user or company with right credentials",
            authorizations = {@Authorization(value="basicAuth")},
            notes = "Returns user/company with corresponding UserDetails object")
    public ResponseEntity<UserDetails> login(@AuthenticationPrincipal UserDetails userDetails){
        UserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userDetails.getUsername());
        if(customUserDetails != null){
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
