package com.example.joobleclone.repositories;

import com.example.joobleclone.models.JobPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long>, PagingAndSortingRepository<JobPost, Long> {
    List<JobPost> findByTitleContainingIgnoreCase(String title);

    @Override
    Page<JobPost> findAll(Pageable pageable);
}
