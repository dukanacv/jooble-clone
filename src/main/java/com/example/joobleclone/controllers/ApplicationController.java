package com.example.joobleclone.controllers;

import com.example.joobleclone.models.AppUser;
import com.example.joobleclone.models.Application;
import com.example.joobleclone.models.Company;
import com.example.joobleclone.models.JobPost;
import com.example.joobleclone.repositories.AppUserRepository;
import com.example.joobleclone.repositories.CompanyRepository;
import com.example.joobleclone.services.ApplicationService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/applications")
@ApiModel(value = "ApplicationController", description = "Controller for testing application related logic")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final CompanyRepository companyRepository;
    private final AppUserRepository appUserRepository;

    public ApplicationController(ApplicationService applicationService,
                                 CompanyRepository companyRepository,
                                 AppUserRepository appUserRepository) {
        this.applicationService = applicationService;
        this.companyRepository = companyRepository;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/apply")
    @ApiOperation(value = "Apply for job method", notes = "Returns http status")
    public ResponseEntity<Void> applyForJob(@RequestBody Application application,
                                            @RequestParam(value = "appUserId")  Long appUserId,
                                            @RequestParam(value = "jobPostId") Long jobPostId,
                                            @AuthenticationPrincipal UserDetails userDetails){
        AppUser appUserToCheck = appUserRepository.findByUsername(userDetails.getUsername());
        if(!Objects.equals(appUserToCheck.getId(), appUserId)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        applicationService.applyForJob(jobPostId, appUserId, application);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all/{jobPostId}")
    @ApiOperation(value = "Get all applications for inserted job post id", notes = "Returns list of applications")
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

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete application with certain id", notes = "Returns message with status")
    public ResponseEntity<String> deleteApplicationById(@PathVariable Long id){
        applicationService.deleteApplicationById(id);
        return new ResponseEntity<>("Application deleted", HttpStatus.OK);
    }
}
