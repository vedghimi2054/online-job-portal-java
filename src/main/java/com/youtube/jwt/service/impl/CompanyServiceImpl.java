package com.youtube.jwt.service.impl;

import com.youtube.jwt.dao.CompanyDao;
import com.youtube.jwt.entity.Recruiters;
import com.youtube.jwt.exception.ResourceNotFoundException;
import com.youtube.jwt.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyDao companyDao;

    public CompanyServiceImpl(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    public Recruiters createRecruiters(Recruiters recruiters) {
        return companyDao.save(recruiters);
    }

    @Override
    public List<Recruiters> getAllRecruiters() {
        return companyDao.findAll();
    }

    @Override
    public Recruiters getAllRecruitersById(Integer comp_id) {
        return companyDao.findById(comp_id).orElseThrow(()->new ResourceNotFoundException("Recruiters","comapny_id",comp_id));
    }

    @Override
    public Recruiters updateRecruiters(Recruiters recruiters, Integer comp_id) {
        Recruiters recruitersExistingDatabase=companyDao.findById(comp_id).orElseThrow(()->new ResourceNotFoundException("Recruiters","comapny_id",comp_id));
        recruitersExistingDatabase.setCompanyName(recruiters.getCompanyName());
        recruitersExistingDatabase.setCompanyLogo(recruiters.getCompanyLogo());
        recruitersExistingDatabase.setPhoneNumber(recruiters.getPhoneNumber());
        recruitersExistingDatabase.setWebsiteUrl(recruiters.getWebsiteUrl());
        recruitersExistingDatabase.setJobPosts(recruiters.getJobPosts());
        companyDao.save(recruitersExistingDatabase);
        return recruitersExistingDatabase;
    }

    @Override
    public void deleteRecruiterById(Integer comp_id) {
        companyDao.findById(comp_id).orElseThrow(()->new ResourceNotFoundException("Recruiters","comapny_id",comp_id));
        companyDao.deleteById(comp_id);
    }
}
