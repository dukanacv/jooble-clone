package com.example.joobleclone.security;

import com.example.joobleclone.models.AppUser;
import com.example.joobleclone.models.Company;
import com.example.joobleclone.repositories.AppUserRepository;
import com.example.joobleclone.repositories.CompanyRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final CompanyRepository companyRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository, CompanyRepository companyRepository) {
        this.appUserRepository = appUserRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser != null){
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_APPUSER"));
            return new User(appUser.getUsername(), appUser.getPassword(), authorities);
        }

        Company company = companyRepository.findByName(username);
        if(company != null){
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_COMPANY"));
            return new User(company.getName(), company.getPassword(), authorities);
        }

        throw new UsernameNotFoundException("Cant find record with that username.");
    }
}
