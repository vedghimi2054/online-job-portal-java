package com.youtube.jwt.controller;

import com.youtube.jwt.controller.base.BaseController;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.User;
import com.youtube.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.List;

@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        if (userService.isUserPresent(user)) {

        }
        return userService.registerNewUser(user);
    }

    @GetMapping("/getAllRegisterUsers")
    public List<User> getAllRegisterUser(User user) {
        return userService.getAllUsers(user);
    }

    //    @DeleteMapping("/deleteRegisteruser/{userid}")
//    public ResponseEntity<String> deleteRegisterUserById(@PathVariable("userid") Integer userId){
//        userService.deleteRegisterUsers(userId);
//        return new ResponseEntity<String>("deleted successfully",HttpStatus.OK);
//    }
    @GetMapping("/searchUser/{query}")
    public ResponseEntity<?> searchUsers(@PathVariable("query") String query, Principal principal) {
        System.out.println(query);
        User user = userDao.findByUserName(principal.getName());
        List<User> users = this.userDao.search(query, user);
        return ResponseEntity.ok(users);
    }
    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin() {
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser() {
        return "This URL is only accessible to the user";
    }
}
