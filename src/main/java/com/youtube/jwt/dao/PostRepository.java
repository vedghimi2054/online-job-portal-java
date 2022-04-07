package com.youtube.jwt.dao;

import com.youtube.jwt.entity.JobPosts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<JobPosts,Integer> {
}
