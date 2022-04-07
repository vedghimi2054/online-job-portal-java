package com.youtube.jwt.controller;

import com.youtube.jwt.entity.ApplyJob;
import com.youtube.jwt.helper.UploadResumeHelper;
import com.youtube.jwt.service.ApplyJobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ApplyJobController {
    private final ApplyJobService applyJobService;

    private final UploadResumeHelper uploadResumeHelper;

    public ApplyJobController(ApplyJobService applyJobService, UploadResumeHelper uploadResumeHelper) {
        this.applyJobService = applyJobService;
        this.uploadResumeHelper = uploadResumeHelper;
    }

    @PostMapping(value = "/postApplyJob",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
//    @PreAuthorize("hasRole('User')")
    public ResponseEntity<ApplyJob> postApplyJob(@RequestPart("applyJob") ApplyJob applyJob, @RequestPart("file") MultipartFile file) {
        String resume = applyJob.getResume();
        if (resume == null) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("request must contain file");
        }
        try {
            if (file.isEmpty()) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("request must contain file");
            }
            if (!file.getContentType().equals("pdf")) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("only pdf content type are allowed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //file uploads
        boolean f=uploadResumeHelper.uploadFile(file);
        if(f){
            return new ResponseEntity<ApplyJob>(applyJobService.createApply(applyJob), HttpStatus.CREATED);
        }
        return new ResponseEntity<ApplyJob>(applyJobService.createApply(applyJob), HttpStatus.CREATED);

    }

    @GetMapping("/getAllApplyJobList")
    public List<ApplyJob> getAllApplyJobList() {
        return applyJobService.getApplyJobList();
    }

    @GetMapping("/id")
    public ResponseEntity<ApplyJob> getAllApplyJobById(@PathVariable("id") Integer applyId) {
        return new ResponseEntity<ApplyJob>(applyJobService.getApplyJobListById(applyId), HttpStatus.OK);
    }
}
