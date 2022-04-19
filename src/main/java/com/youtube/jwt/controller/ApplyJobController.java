package com.youtube.jwt.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube.jwt.controller.base.BaseController;
import com.youtube.jwt.entity.ApplyJob;
import com.youtube.jwt.helper.UploadResumeHelper;
import com.youtube.jwt.service.ApplyJobService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<ApplyJob> downloadFile(@PathVariable("fileName") MultipartFile fileName , HttpServletResponse response){
        ApplyJob applyJob= new ApplyJob();
        boolean file= uploadResumeHelper.uploadFile(fileName);
        if(file){
            applyJob.setResume(fileName.getOriginalFilename());
        }
        String folderPath=uploadResumeHelper.UPLOAD_DIR;
//        if(fileName.indexOf(".doc")>-1){
//            response.setContentType("application/msword");
//        }
//        if(fileName.indexOf(".docx")>-1){
//            response.setContentType("application/msword");
//        }
//        if(fileName.indexOf(".pdf")>-1){
//            response.setContentType("application/pdf");
//        }
        response.setHeader("Content-Disposition","attachment; fileName="+fileName);
        response.setHeader("Content-Transfer-Encoding","binary");
        try {
            BufferedOutputStream bos=new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis= new FileInputStream(folderPath+fileName);
            int len;
            byte[] buf=new byte[1024];
            while((len=fis.read(buf))>0){
                bos.write(buf,0,len);

            }
            bos.close();
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        File file=new File(fileName);
//        InputStreamResource resource=new InputStreamResource(new FileInputStream(file));
        return null;
//
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
