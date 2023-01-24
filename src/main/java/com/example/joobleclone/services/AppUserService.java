package com.example.joobleclone.services;

import com.example.joobleclone.models.AppUser;
import com.example.joobleclone.repositories.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser register(AppUser appUser){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUser.setApplications(new ArrayList<>());
        return appUserRepository.save(appUser);
    }

    public AppUser login(String username, String password){
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser != null && appUser.getPassword().equals(password)){
            return appUser;
        }
        return null;
    }
}
