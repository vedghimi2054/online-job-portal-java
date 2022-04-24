package com.youtube.jwt.service.impl;

import com.youtube.jwt.dao.ApplyJobRepository;
import com.youtube.jwt.entity.ApplyJob;
import com.youtube.jwt.exception.ResourceNotFoundException;
import com.youtube.jwt.service.ApplyJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ApplyJobServiceImpl implements ApplyJobService {
    @Autowired
    private ApplyJobRepository applyJobRepository;
    @Override
    public ApplyJob createApply(ApplyJob applyJob) {
        return applyJobRepository.save(applyJob);
    }

    @Override
    public List<ApplyJob> getApplyJobList() {
        return applyJobRepository.findAll();
    }
    @Override
    public ApplyJob getApplyJobListById(Integer applyId) {
        return applyJobRepository.findById(applyId).orElseThrow(()->new ResourceNotFoundException("Apply job","ID",applyId));
    }

    @Override
    public void deleteApplyJobList() {
        applyJobRepository.deleteAll();
    }
}
