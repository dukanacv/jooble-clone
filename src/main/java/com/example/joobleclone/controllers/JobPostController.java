package com.example.joobleclone.controllers;

import com.example.joobleclone.models.Company;
import com.example.joobleclone.models.JobPost;
import com.example.joobleclone.repositories.CompanyRepository;
import com.example.joobleclone.services.CompanyService;
import com.example.joobleclone.services.JobPostService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job-posts")
public class JobPostController {

    private final JobPostService jobPostService;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    public JobPostController(JobPostService jobPostService, CompanyService companyService,
                             CompanyRepository companyRepository) {
        this.jobPostService = jobPostService;
        this.companyService = companyService;
        this.companyRepository = companyRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<JobPost>> getAllJobs(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10")int size){
        Page<JobPost> allJobPosts = jobPostService.getAllJobPosts(page, size);
        return new ResponseEntity<>(allJobPosts, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createJob(@RequestBody JobPost jobPost,
                                            @RequestParam(value = "companyId") Long companyId,
                                            @AuthenticationPrincipal UserDetails companyDetails){
        Company company = companyRepository.findById(companyId).get();
        if(!company.getUsername().equals(companyDetails.getUsername())){
            return new ResponseEntity<>("Error creating job", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        JobPost newJobPost = companyService.postJob(jobPost, companyId);
        if(newJobPost != null){
            return new ResponseEntity<>("Job created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error creating job", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/search")
    public ResponseEntity<List<JobPost>> searchJobPostsByTitle(@RequestParam(required = false) String title){
        List<JobPost> searchedJobPosts = jobPostService.searchJobsByTitle(title);
        return new ResponseEntity<>(searchedJobPosts, HttpStatus.OK);
    }
}
