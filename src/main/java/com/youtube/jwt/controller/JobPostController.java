package com.youtube.jwt.controller;
import com.youtube.jwt.controller.base.BaseController;
import com.youtube.jwt.dao.PostRepository;
import com.youtube.jwt.entity.JobPosts;
import com.youtube.jwt.entity.PostStatus;
import com.youtube.jwt.service.impl.PostServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class JobPostController extends BaseController {
    private PostServiceImpl postService;
    private PostRepository postRepository;

    public JobPostController(PostServiceImpl postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @PostMapping("/jobpost/createpost")
    @PreAuthorize("hasRole('Recruiter')")
    public ResponseEntity<JobPosts> createPost(@RequestBody JobPosts jobPosts, Principal principal) {
        jobPosts.setStatus(PostStatus.PENDING);
        jobPosts.setUserName(principal.getName());
        return new ResponseEntity<JobPosts>(postService.createJobPost(jobPosts), HttpStatus.CREATED);
    }

    @GetMapping("jobpost/approvePost/{postId}")
    @PreAuthorize("hasRole('Admin')")
    public JobPosts approveJobPost(@PathVariable Integer postId, HttpServletRequest request) {
        String value=request.getParameter("status");
        System.out.println("value from frontend"+value);
        JobPosts jobPosts = postRepository.findById(postId).get();
        jobPosts.setStatus(PostStatus.APPROVED);
        return postRepository.save(jobPosts);
    }

    @GetMapping("jobpost/rejectPost/{postId}")
    @PreAuthorize("hasRole('Admin')")
    public JobPosts removePost(@PathVariable Integer postId) {
        JobPosts post = postRepository.findById(postId).get();
        post.setStatus(PostStatus.REJECTED);
        return postRepository.save(post);
    }

    @GetMapping("jobpost/viewAll")
//    @PreAuthorize("hasRole('Admin')")
    public List<JobPosts> viewAll() {
        return ((Collection<JobPosts>) postRepository.findAll()).stream().filter(post -> post.getStatus().equals(PostStatus.APPROVED)).collect(Collectors.toList());
    }

    @GetMapping("jobpost/getAllPosts")
//    @PreAuthorize("hasRole('Recruiter')")
    public List<JobPosts> getAllPosts() {
        return postService.getAllPost();
    }

    @GetMapping("jobpost/{id}")
    public ResponseEntity<JobPosts> getAllPostById(@PathVariable("id") Integer jobId) {
        return new ResponseEntity<JobPosts>(postService.getAllPostById(jobId), HttpStatus.OK);
    }
    @PutMapping("jobpost/{id}")
    public ResponseEntity<JobPosts> updateJobPost(@RequestBody JobPosts jobPosts, @PathVariable("id") Integer jobId) {
        return new ResponseEntity<JobPosts>(postService.updateJobPost(jobPosts, jobId), HttpStatus.OK);
    }

    @DeleteMapping("jobpost/deletePost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Integer jobId) {
        postService.deleteJobPost(jobId);
        return new ResponseEntity<>("delete successfully", HttpStatus.OK);
    }
}
