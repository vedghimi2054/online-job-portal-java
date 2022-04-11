package com.youtube.jwt.dao;

import com.youtube.jwt.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface UserDao extends CrudRepository<User, String> {
    @Query("SELECT u FROM User u WHERE CONCAT(U.userName,U.userFirstName,U.userLastName,U.userEmail) LIKE %?1%")
    User findByUserName(String username);
//    @Query("SELECT u FROM User u WHERE u.userName LIKE %?1%" +
//            "OR u.userFirstName LIKE %?1%" +
//            "OR u.userLastName LIKE %?1%" +
//            "OR U.userEmail LIKE %?1%")
    List<User> search(String keyword,User user);
}
