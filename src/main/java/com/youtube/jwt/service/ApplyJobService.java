package com.youtube.jwt.service;

import com.youtube.jwt.entity.ApplyJob;

import java.util.List;

public interface ApplyJobService {
    ApplyJob createApply(ApplyJob applyJob);
    List<ApplyJob> getApplyJobList();
    ApplyJob getApplyJobListById(Integer applyId);
    void deleteApplyJobList();
}
