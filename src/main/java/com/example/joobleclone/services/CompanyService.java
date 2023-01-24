package com.example.joobleclone.services;

import com.example.joobleclone.models.Application;
import com.example.joobleclone.models.Company;
import com.example.joobleclone.models.JobPost;
import com.example.joobleclone.repositories.ApplicationRepository;
import com.example.joobleclone.repositories.CompanyRepository;
import com.example.joobleclone.repositories.JobPostRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final JobPostRepository jobPostRepository;
    private final ApplicationRepository applicationRepository;

    public CompanyService(CompanyRepository companyRepository,
                          JobPostRepository jobPostRepository,
                          ApplicationRepository applicationRepository) {
        this.companyRepository = companyRepository;
        this.jobPostRepository = jobPostRepository;
        this.applicationRepository = applicationRepository;
    }

    public Company register(Company company){
        Company companyCheckName = companyRepository.findByName(company.getName());
        if(companyCheckName != null){
            throw new IllegalArgumentException("A company with that name already exists.");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(company.getPassword());
        company.setPassword(encodedPassword);
        company.setJobPosts(new ArrayList<>());
        return companyRepository.save(company);
    }

    public Company login(String name, String password){
        Company company = companyRepository.findByName(name);
        if(company != null && company.getPassword().equals(password)){
            return company;
        }
        return null;
    }

    public JobPost postJob(JobPost jobPost, Long companyId){
        Company company = companyRepository.findById(companyId).get();
        jobPost.setCompany(company);
        jobPost.setCreatedAt(new Date());
        return jobPostRepository.save(jobPost);
    }

    public Application updateApplicationStatus(Long applicationId, String status){
        Application application = applicationRepository.findById(applicationId).get();
        application.setStatus(status);
        return applicationRepository.save(application);
    }
}
