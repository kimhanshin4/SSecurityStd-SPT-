package com.study.springsecuritystudy.user.controller;

import com.study.springsecuritystudy.security.JwtUtil;
import com.study.springsecuritystudy.user.dto.*;
import com.study.springsecuritystudy.user.entity.UserRoleEnum;
import com.study.springsecuritystudy.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//회원가입,로그인,로그인이정상적으로될시jwt
@RestController
@RequestMapping("/api")
@Slf4j(topic = "로그인 및 JWT 생성")
public class UserController {

    final UserService userService;
    final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/jwt")
    public ResponseEntity<String> createJWT(){
        String username = "Funold";
        UserRoleEnum role = UserRoleEnum.USER;

        return new ResponseEntity<>(jwtUtil.createToken(username, role), HttpStatus.OK);
    }
//m
    @GetMapping("/users")
    public ResponseEntity<String> signup(@RequestBody UserRequestDto requestDto){
        userService.signup(requestDto);
        return new ResponseEntity<>("좋아, 새 일원 이로군!",HttpStatus.OK);
    }
    @PostMapping("/users")
    public ResponseEntity<String>login(@RequestBody UserRequestDto requestDto){
        userService.login(requestDto);
        return new ResponseEntity<>("아 통과!",HttpStatus.OK);
    }
    @PostMapping

}
