package com.youtube.jwt.controller;

import com.youtube.jwt.entity.JobPosts;
import com.youtube.jwt.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobpost")
@CrossOrigin(origins = {
        "192.168.88.79:3000",
        "http://localhost:3000"
})
public class JobPostController {
    @Autowired
    private PostServiceImpl postService;

    @PostMapping("/createpost")
    public ResponseEntity<JobPosts> createPost(@RequestBody JobPosts jobPosts) {
        return new ResponseEntity<JobPosts>(postService.createJobPost(jobPosts), HttpStatus.CREATED);
    }
    @GetMapping("/getAllPosts")
    public List<JobPosts> getAllPosts(){
        return postService.getAllPost();
    }
    @GetMapping("{id}")
    public ResponseEntity<JobPosts> getAllPostById(@PathVariable("id") Integer jobId){
        return new ResponseEntity<JobPosts>(postService.getAllPostById(jobId),HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<JobPosts> updateJobPost(@RequestBody JobPosts jobPosts,@PathVariable("id") Integer jobId){
       return new ResponseEntity<JobPosts>(postService.updateJobPost(jobPosts,jobId),HttpStatus.OK);
    }
    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Integer jobId){
        postService.deleteJobPost(jobId);
        return new ResponseEntity<>("delete successfully",HttpStatus.OK);
    }
}
