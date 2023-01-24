package com.example.joobleclone.repositories;

import com.example.joobleclone.models.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findByTitleContainingIgnoreCase(String title);
}
