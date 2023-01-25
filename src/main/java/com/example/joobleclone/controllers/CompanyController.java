package com.example.joobleclone.controllers;

import com.example.joobleclone.dtos.LoginDto;
import com.example.joobleclone.models.Company;
import com.example.joobleclone.services.CompanyService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
@ApiModel(value = "CompanyController", description = "Controller for testing registration of new company 'user'")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/register")
    @ApiOperation(value = "Register new company user", notes = "Returns company model")
    public ResponseEntity<Company> register(@RequestBody Company company){
        Company companyToRegister = companyService.register(company);
        return new ResponseEntity<>(companyToRegister, HttpStatus.CREATED);
    }

//    @PostMapping("/login")
//    public ResponseEntity<Company> login(@RequestBody LoginDto loginDto){
//        Company company = companyService.login(loginDto.getUsername(), loginDto.getPassword());
//        if(company != null){
//            return new ResponseEntity<>(company, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }
}
