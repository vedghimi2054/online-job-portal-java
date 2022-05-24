package com.youtube.jwt.dao;

import com.youtube.jwt.entity.JobPosts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<JobPosts,Integer> {

    @Query(value = "select * from job_posts where concat(company_name,description,job_title,last_date,post_date,user_name) like %?1%",nativeQuery = true)
    List<JobPosts> searchJobPostsBy(String keyword);
}
