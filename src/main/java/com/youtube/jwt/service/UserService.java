package com.youtube.jwt.service;

import com.youtube.jwt.dao.RoleDao;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.Role;
import com.youtube.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        Role recruiterRole = new Role();
        recruiterRole.setRoleName("Recruiter");
        recruiterRole.setRoleDescription("Default role for newly created record");
        roleDao.save(recruiterRole);
        //for recruiters
        User recruitersUser = new User();
        recruitersUser.setUserName("recruiters@123");
        recruitersUser.setUserPassword(getEncodedPassword("recruiters123"));
//        System.out.println("Password: " + recruitersUser.getUserPassword());
        recruitersUser.setUserEmail("recruiters@gmail.com");
        recruitersUser.setUserFirstName("recruiters");
        recruitersUser.setUserLastName("recruiters");
        Set<Role> recruitersRoles = new HashSet<>();
        recruitersRoles.add(recruiterRole);
        recruitersUser.setRole(recruitersRoles);
        userDao.save(recruitersUser);
//for admin
        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setUserEmail("admin@gmail.com");
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);
    }

    public User registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return userDao.save(user);
    }

    public List<User> getAllUsers() {
        return (List<User>) userDao.findAll();
    }

    //    public void deleteRegisterUsers(Integer userId){
//        userDao.deleteById(String.valueOf(userId));
//    }
    public boolean isUserPresent(User user) {
        boolean userExists = false;
        User existingUser = userDao.findByUserName(user.getUserName());
        User existingUserEmail=userDao.findByUserEmail(user.getUserEmail());
        if (existingUser != null) {
            userExists = true;
        }
        if(existingUserEmail!=null){
            userExists=true;
        }
        return userExists;
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
