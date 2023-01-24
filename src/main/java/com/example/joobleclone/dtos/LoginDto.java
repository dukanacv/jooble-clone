package com.example.joobleclone.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class LoginDto {

    @NotNull
    @Length(min = 5, max = 50)
    private String username;

    @NotNull
    @Length(min = 5, max = 50)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
