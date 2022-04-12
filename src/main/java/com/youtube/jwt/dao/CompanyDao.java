package com.youtube.jwt.dao;

import com.youtube.jwt.entity.Recruiters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDao extends JpaRepository<Recruiters,Integer> {
    
}
