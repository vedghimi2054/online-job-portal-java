package com.youtube.jwt.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.youtube.jwt.ApiResponse.ApiResponse;
import com.youtube.jwt.ApiResponse.ApplyJobResponse;
import com.youtube.jwt.controller.base.BaseController;
import com.youtube.jwt.dto.customDto.ContactDto;
import com.youtube.jwt.entity.ApplyJob;
import com.youtube.jwt.payload.UploadFileResponse;
import com.youtube.jwt.service.ApplyJobService;
import com.youtube.jwt.service.FileStorageService;
import com.youtube.jwt.service.impl.JobMailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RestController
public class ApplyJobController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ApplyJobController.class);
    private final ApplyJobService applyJobService;
    private JobMailServiceImpl jobMailService;

    private final FileStorageService fileStorageService;

    public ApplyJobController(ApplyJobService applyJobService, FileStorageService fileStorageService,JobMailServiceImpl jobMailService) {
        this.applyJobService = applyJobService;
        this.fileStorageService = fileStorageService;
        this.jobMailService=jobMailService;
    }

    @PostMapping(value = "/postApplyJob",produces = "application/json", headers = "Accept=application/json")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<ApiResponse> postApplyJob(@RequestPart("applyJob") String applyJobStr, @RequestPart("file") MultipartFile file) throws IOException, MessagingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        ApplyJob applyJob = mapper.readValue(applyJobStr, ApplyJob.class);
        try {
            if (file.isEmpty()) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("request must contain file");
            }
            if (!file.getContentType().equals("application/pdf")) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("only pdf content type are allowed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        UploadFileResponse response = new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        applyJob.setResume(file.getOriginalFilename());
        ApplyJob applyJob1 = applyJobService.createApply(applyJob);
        ApplyJobResponse applyJobResponse = new ApplyJobResponse();
        applyJobResponse.setUploadFileResponse(response);
        applyJobResponse.setApplyJob(applyJob1);
        jobMailService.sendNotificationToUsers(applyJob1);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Saved",applyJobResponse),HttpStatus.OK);
    }


    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/getAllApplyJobList")
    @PreAuthorize("hasRole('Recruiter')")
    public ResponseEntity<ApiResponse> getAllApplyJobList() {
        List<ApplyJob> applyJobList=applyJobService.getApplyJobList();
        return new ResponseEntity<>(new ApiResponse("success",applyJobList),HttpStatus.OK);
    }

    @GetMapping("/getAllApplyJobList/{id}")
    public ResponseEntity<ApiResponse> getAllApplyJobById(@PathVariable("id") Integer applyId) {
        ApplyJob applyJobList=applyJobService.getApplyJobListById(applyId);
        return new ResponseEntity<>(new ApiResponse("success",applyJobList), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllApplyJobList")
    public ResponseEntity<String> deleteAllApplyJobList() {
        applyJobService.deleteApplyJobList();
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }
    @PostMapping("/contact")
    public ResponseEntity<ApiResponse> sendContactMail(@RequestBody ContactDto contactDto) throws MessagingException {
        if(contactDto==null){
            return new ResponseEntity<>(new ApiResponse("Field must not be null"),HttpStatus.NO_CONTENT);
        }
        ContactDto contactDto1 = contactDto;
        contactDto1 = contactDto;
        String body = " Query send from  "+ contactDto1.getFirstName() +" "+ contactDto1.getMiddleName() +" "+ contactDto1.getLastName()+
                "  his/her email address is : " + contactDto1.getEmail() + "  description about query : " + contactDto1.getDescription();


        jobMailService.sendContactMail(body, body);

        return new ResponseEntity<>(new ApiResponse("Mail send Successfully"),HttpStatus.OK);

    }
}

