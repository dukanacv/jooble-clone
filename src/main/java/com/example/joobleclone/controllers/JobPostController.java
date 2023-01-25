package com.example.joobleclone.controllers;

import com.example.joobleclone.dtos.Views;
import com.example.joobleclone.models.Company;
import com.example.joobleclone.models.JobPost;
import com.example.joobleclone.repositories.CompanyRepository;
import com.example.joobleclone.services.CompanyService;
import com.example.joobleclone.services.JobPostService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job-posts")
@ApiModel(value = "JobPostController", description = "Controller for testing functionalities related to job posts")
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


    @JsonView(Views.Public.class)
    @GetMapping("/all")
    @ApiOperation(value = "Get all job posts", notes = "Returns paginated list of job posts")
    public ResponseEntity<Page<JobPost>> getAllJobs(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10")int size){
        Page<JobPost> allJobPosts = jobPostService.getAllJobPosts(page, size);
        return new ResponseEntity<>(allJobPosts, HttpStatus.OK);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a job post", notes = "Returns message with http status")
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
    @ApiOperation(value = "Search through job posts by provided keyword",
            notes = "Returns list of found job posts according to keyword")
    public ResponseEntity<List<JobPost>> searchJobPostsByTitle(@RequestParam(required = false) String title){
        List<JobPost> searchedJobPosts = jobPostService.searchJobsByTitle(title);
        return new ResponseEntity<>(searchedJobPosts, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete job post with certain id", notes = "Returns message with http status")
    public ResponseEntity<String> deleteJobPostById(@PathVariable Long id){
        jobPostService.deleteJobPostById(id);
        return new ResponseEntity<>("Job post deleted", HttpStatus.OK);
    }
}
