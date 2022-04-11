package com.youtube.jwt.controller.base;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {
        "192.168.88.79:3000",
        "http://localhost:3000"
},allowedHeaders = ("*"))
public class BaseController {
}

