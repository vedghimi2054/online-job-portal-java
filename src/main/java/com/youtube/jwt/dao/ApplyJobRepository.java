package com.youtube.jwt.dao;

import com.youtube.jwt.entity.ApplyJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyJobRepository extends JpaRepository<ApplyJob,Integer> {
}
