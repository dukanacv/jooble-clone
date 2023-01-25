package com.example.joobleclone.services;

import com.example.joobleclone.models.AppUser;
import com.example.joobleclone.models.Application;
import com.example.joobleclone.models.JobPost;
import com.example.joobleclone.repositories.AppUserRepository;
import com.example.joobleclone.repositories.ApplicationRepository;
import com.example.joobleclone.repositories.JobPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobPostRepository jobPostRepository;
    private final AppUserRepository appUserRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              JobPostRepository jobPostRepository,
                              AppUserRepository appUserRepository) {
        this.applicationRepository = applicationRepository;
        this.jobPostRepository = jobPostRepository;
        this.appUserRepository = appUserRepository;
    }

    public void applyForJob(Long jobPostId, Long appUserId, Application application){
        JobPost jobPost = jobPostRepository.findById(jobPostId).get();
        AppUser appUser = appUserRepository.findById(appUserId).get();

        application.setJobPost(jobPost);
        application.setAppUser(appUser);

        applicationRepository.save(application);
    }

    public List<Application> getApplicationsForJobPostId(Long jobPostId){
        return applicationRepository.findByJobPostId(jobPostId);
    }

    public void deleteApplicationById(Long id){
        applicationRepository.deleteById(id);
    }
}
