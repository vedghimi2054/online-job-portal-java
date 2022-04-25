package com.youtube.jwt.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube.jwt.controller.base.BaseController;
import com.youtube.jwt.entity.ApplyJob;
import com.youtube.jwt.helper.UploadResumeHelper;
import com.youtube.jwt.payload.UploadFileResponse;
import com.youtube.jwt.service.ApplyJobService;
import com.youtube.jwt.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class ApplyJobController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ApplyJobController.class);
    private final ApplyJobService applyJobService;

    @Autowired
    private FileStorageService fileStorageService;
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
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        UploadFileResponse response=new UploadFileResponse(fileName,fileDownloadUri,file.getContentType(),file.getSize());
        applyJob.setResume(file.getOriginalFilename());
        return new ResponseEntity<ApplyJob>(applyJobService.createApply(applyJob), HttpStatus.CREATED);
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
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
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
    @DeleteMapping("/deleteAllApplyJobList")
    public ResponseEntity<String> deleteAllApplyJobList(){
        applyJobService.deleteApplyJobList();
        return new ResponseEntity<String>("deleted successfully",HttpStatus.OK);
    }
}
