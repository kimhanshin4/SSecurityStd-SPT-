package com.study.springsecuritystudy.user.dto;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthTestController {

    @GetMapping
    public ResponseEntity<String> test() {

    }
}
