package com.youtube.jwt.controller;

import com.youtube.jwt.controller.base.BaseController;
import com.youtube.jwt.entity.User;
import com.youtube.jwt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class UserController extends BaseController {

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public ResponseEntity<?> registerNewUser(@RequestBody User user) {
        if (userService.isUserPresent(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A user with that username already exists");
        }
        return ResponseEntity.ok(userService.registerNewUser(user));
    }

    @GetMapping("/getAllRegisterUsers")
    public List<User> getAllRegisterUser() {
        return userService.getAllUsers();
    }

    //    @DeleteMapping("/deleteRegisteruser/{userid}")
//    public ResponseEntity<String> deleteRegisterUserById(@PathVariable("userid") Integer userId){
//        userService.deleteRegisterUsers(userId);
//        return new ResponseEntity<String>("deleted successfully",HttpStatus.OK);
//    }
//    @GetMapping("/searchUser/{query}")
//    public ResponseEntity<?> searchUsers(@PathVariable("query") String query, Principal principal) {
//        System.out.println(query);
//        User user = userDao.findByUserName(principal.getName());
//        List<User> users = this.userDao.search(query, user);
//        return ResponseEntity.ok(users);
//    }
}
