package com.example.joobleclone.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_post_id")
    @JsonIgnore
    private JobPost jobPost;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    @JsonIgnore
    private AppUser appUser;

    private String resume;

    private String status;

    public Application() {
    }

    public Application(Long id, JobPost jobPost, AppUser appUser, String resume, String status) {
        this.id = id;
        this.jobPost = jobPost;
        this.appUser = appUser;
        this.resume = resume;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
