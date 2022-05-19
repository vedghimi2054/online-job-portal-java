package com.youtube.jwt.service;

import com.youtube.jwt.entity.Recruiters;
import java.util.List;

public interface CompanyService {
    Recruiters createRecruiters(Recruiters recruiters);

    List<Recruiters> getAllRecruiters();

    Recruiters getAllRecruitersById(Integer comp_id);

    Recruiters updateRecruiters(Recruiters recruiters, Integer comp_id);

    void deleteRecruiterById(Integer comp_id);
}
