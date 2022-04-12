package com.youtube.jwt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube.jwt.entity.Recruiters;
import com.youtube.jwt.helper.UploadResumeHelper;
import com.youtube.jwt.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UploadResumeHelper uploadResumeHelper;

    @PostMapping("/createRecruiters")
    public ResponseEntity<Recruiters> createRecruiters(@RequestPart("addRecruiter") String recruiterstr, @RequestPart("image") MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        Recruiters recruiters=objectMapper.readValue(recruiterstr,Recruiters.class);
        try{
            if(file.isEmpty()){
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Must contain image file");
            }
            if(!file.getContentType().equals("image/jpg")){
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request must contain jpg only");
            }
            boolean f=uploadResumeHelper.uploadFile(file);
            if(f){
                recruiters.setCompanyLogo(file.getOriginalFilename());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<Recruiters>(companyService.createRecruiters(recruiters), HttpStatus.CREATED);
    }
    @GetMapping("/getAllRecruiters")
    public List<Recruiters> getAllRecruiters(){
        return companyService.getAllRecruiters();
    }
    @GetMapping("/getAllRecruiters/{recruiterId}")
    public ResponseEntity<Recruiters> getAllRecruitersById(@PathVariable("recruiterId") Integer rec_id){
        return new ResponseEntity<Recruiters>(companyService.getAllRecruitersById(rec_id),HttpStatus.OK);
    }
    @PutMapping("/updateRecruiters/{id}")
    public ResponseEntity<Recruiters> updateRecruiters(@RequestBody Recruiters recruiters,@PathVariable("id") Integer comp_id){
        return new ResponseEntity<>(companyService.updateRecruiters(recruiters,comp_id),HttpStatus.OK);
    }
    @DeleteMapping("/deleteRecruitersById/{id}")
    public ResponseEntity<String> deleteRecruitersById(@PathVariable("id") Integer comp_id){
        companyService.deleteRecruiterById(comp_id);
        return new ResponseEntity<String>("delete Successfully",HttpStatus.OK);
    }
}