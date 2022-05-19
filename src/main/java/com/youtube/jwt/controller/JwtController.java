package com.youtube.jwt.controller;

import com.youtube.jwt.controller.base.BaseController;
import com.youtube.jwt.Security.JwtRequest;
import com.youtube.jwt.Security.JwtResponse;
import com.youtube.jwt.service.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController extends BaseController {

    private JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
}
