package com.youtube.jwt.controller;

import com.youtube.jwt.controller.base.BaseController;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.User;
import com.youtube.jwt.exception.NotFoundException;
import com.youtube.jwt.service.UserService;
import com.youtube.jwt.service.impl.JobMailServiceImpl;
import com.youtube.jwt.util.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
public class ForgetPasswordController extends BaseController {
    private UserService userService;
    private UserDao userDao;

    private JobMailServiceImpl jobMailService;

    public ForgetPasswordController(UserService userService, JobMailServiceImpl jobMailService,UserDao userDao) {
        this.userService = userService;
        this.jobMailService = jobMailService;
        this.userDao=userDao;

    }

    @PostMapping("/forget_password")
    public String processForgetPassword(@RequestParam("email") String userEmail, HttpServletRequest request) {
        String token = RandomString.make(32);
        System.out.println("token"+token);
        try {
            userService.updateResetPasswordToken(token, userEmail);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            jobMailService.sendEmail(userEmail, resetPasswordLink);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new NotFoundException("error" + e.getMessage());

        }
        return "successfully send email please check your email";

    }
    @PostMapping("/reset_password")
    public String processResetPassword(@Param("token") String token, @RequestParam("password") String password){
        User user=userService.getByResetPasswordToken(token);
        if(user==null){
            return "invalid token";
        }
        else{
            userService.updatePassword(user,password);
            return "you have successfully changed your password";
        }
    }
}
