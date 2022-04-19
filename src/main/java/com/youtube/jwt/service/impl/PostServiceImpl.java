package com.youtube.jwt.service.impl;
import com.youtube.jwt.dao.PostRepository;
import com.youtube.jwt.entity.JobPosts;
import com.youtube.jwt.exception.ResourceNotFoundException;
import com.youtube.jwt.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public JobPosts createJobPost(JobPosts jobPosts) {
        return postRepository.save(jobPosts);
    }

    @Override
    public List<JobPosts> getAllPost() {
        return (List<JobPosts>) postRepository.findAll();

    }

    @Override
    public JobPosts getAllPostById(Integer jobId) {
        return postRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job Post", "ID", jobId));
    }

    @Override
    public JobPosts updateJobPost(JobPosts jobPosts, Integer jobId) {
        JobPosts jobPostsExistingDatabase = postRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job Post", "ID", jobId));
        jobPostsExistingDatabase.setCompanyName(jobPosts.getCompanyName());
        jobPostsExistingDatabase.setJobTitle(jobPosts.getJobTitle());
        jobPostsExistingDatabase.setPostDate(jobPosts.getPostDate());
        jobPostsExistingDatabase.setLastDate(jobPosts.getLastDate());
        jobPostsExistingDatabase.setStatus(jobPosts.getStatus());
        postRepository.save(jobPostsExistingDatabase);
        return jobPostsExistingDatabase;
    }

    @Override
    public void deleteJobPost(Integer jobId) {
            postRepository.deleteById(jobId);
    }
}
