package com.youtube.jwt.dao;

import com.youtube.jwt.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface UserDao extends CrudRepository<User, String> {
//    @Query(value = "SELECT u FROM User u WHERE CONCAT(U.userName,U.userFirstName,U.userLastName,U.userEmail) LIKE %?1%")
    User findByUserName(String username);

//    @Query(value = "select * from users where email=?1",nativeQuery = true)
    User findByUserEmail(String email);
//    @Query(value = "SELECT * FROM users WHERE token = ?1",nativeQuery = true)
    User findByResetPasswordToken(String token);
}
