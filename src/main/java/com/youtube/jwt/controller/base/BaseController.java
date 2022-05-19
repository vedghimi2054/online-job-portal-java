package com.youtube.jwt.controller.base;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = {
        "192.168.88.12:3000",
        "http://localhost:3000"
},allowedHeaders = ("*"))
@RequestMapping("/api")
public class BaseController {
}

