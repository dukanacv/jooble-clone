package com.example.joobleclone.services;

import com.example.joobleclone.models.JobPost;
import com.example.joobleclone.repositories.JobPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;

    public JobPostService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public Page<JobPost> getAllJobPosts(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return jobPostRepository.findAll(pageable);
    }

    public List<JobPost> searchJobsByTitle(String title){
        return jobPostRepository.findByTitleContainingIgnoreCase(title);
    }
}