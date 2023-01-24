package com.example.joobleclone.controllers;

import com.example.joobleclone.models.Application;
import com.example.joobleclone.models.Company;
import com.example.joobleclone.models.JobPost;
import com.example.joobleclone.repositories.CompanyRepository;
import com.example.joobleclone.services.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final CompanyRepository companyRepository;

    public ApplicationController(ApplicationService applicationService,
                                 CompanyRepository companyRepository) {
        this.applicationService = applicationService;
        this.companyRepository = companyRepository;
    }

    @PostMapping("/apply")
    public ResponseEntity<Void> applyForJob(@RequestBody Application application,
                                            @RequestParam(value = "appUserId")  Long appUserId,
                                            @RequestParam(value = "jobPostId") Long jobPostId){
        applicationService.applyForJob(jobPostId, appUserId, application);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all/{jobPostId}")
    public ResponseEntity<List<Application>> getApplicationsForJobPostId(@PathVariable Long jobPostId,
                                                                         @AuthenticationPrincipal UserDetails companyDetails){
        Company company = companyRepository.findByName(companyDetails.getUsername());
        List<Long> jobPostsIds = company.getJobPosts().stream().map(JobPost::getId).collect(Collectors.toList());
        if(!jobPostsIds.contains(jobPostId)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<Application> applicationsByJobPostId = applicationService.getApplicationsForJobPostId(jobPostId);
        return new ResponseEntity<>(applicationsByJobPostId, HttpStatus.OK);
    }
}
