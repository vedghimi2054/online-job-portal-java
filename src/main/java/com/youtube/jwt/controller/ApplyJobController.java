package com.youtube.jwt.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube.jwt.controller.base.BaseController;
import com.youtube.jwt.entity.ApplyJob;
import com.youtube.jwt.helper.UploadResumeHelper;
import com.youtube.jwt.service.ApplyJobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
public class ApplyJobController extends BaseController {
    private final ApplyJobService applyJobService;

    private final UploadResumeHelper uploadResumeHelper;

    public ApplyJobController(ApplyJobService applyJobService, UploadResumeHelper uploadResumeHelper) {
        this.applyJobService = applyJobService;
        this.uploadResumeHelper = uploadResumeHelper;
    }

    @PostMapping(value = "/postApplyJob")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<ApplyJob> postApplyJob(@RequestPart("applyJob") String applyJobStr, @RequestPart("file") MultipartFile file) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ApplyJob applyJob = mapper.readValue(applyJobStr, ApplyJob.class);
        try {
            if (file.isEmpty()) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("request must contain file");
            }
            if (!file.getContentType().equals("application/pdf")) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("only pdf content type are allowed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("file name"+file.getContentType());
        //file uploads
        boolean f=uploadResumeHelper.uploadFile(file);
        if(f){
            applyJob.setResume(file.getOriginalFilename());
        }
        return new ResponseEntity<ApplyJob>(applyJobService.createApply(applyJob), HttpStatus.CREATED);

    }

    @GetMapping("/getAllApplyJobList")
//    @PreAuthorize("hasRole('Recruiter')")
    public List<ApplyJob> getAllApplyJobList() {
        return applyJobService.getApplyJobList();
    }

    @GetMapping("/id")
    public ResponseEntity<ApplyJob> getAllApplyJobById(@PathVariable("id") Integer applyId) {
        return new ResponseEntity<ApplyJob>(applyJobService.getApplyJobListById(applyId), HttpStatus.OK);
    }
}
