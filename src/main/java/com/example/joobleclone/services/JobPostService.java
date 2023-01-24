package com.example.joobleclone.services;

import com.example.joobleclone.models.JobPost;
import com.example.joobleclone.repositories.JobPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;

    public JobPostService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public List<JobPost> getAllJobPosts(){
        return jobPostRepository.findAll();
    }

    public List<JobPost> searchJobsByTitle(String title){
        return jobPostRepository.findByTitleContainingIgnoreCase(title);
    }
}
