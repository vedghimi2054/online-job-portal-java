package com.youtube.jwt.service;
import com.youtube.jwt.entity.JobPosts;

import java.text.ParseException;
import java.util.List;

public interface PostService {
    JobPosts createJobPost(JobPosts jobPosts);
    List<JobPosts> getAllPost();
    JobPosts getAllPostById(Integer jobId);
    JobPosts updateJobPost(JobPosts jobPosts,Integer jobId);
    void deleteJobPost(Integer jobId);
}
