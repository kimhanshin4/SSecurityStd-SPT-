package com.study.springsecuritystudy.user.controller;

import com.study.springsecuritystudy.security.*;
import com.study.springsecuritystudy.user.dto.*;
import com.study.springsecuritystudy.user.dto.response.*;
import com.study.springsecuritystudy.user.entity.*;
import com.study.springsecuritystudy.user.service.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

//회원가입,로그인,로그인이정상적으로될시jwt
@RestController
@RequestMapping("/api")
@Slf4j(topic = "로그인 및 JWT 생성")
public class UserController {

    final UserService userService;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> createJWT(@RequestBody UserRequestDto requestDto) {
        return new ResponseEntity<>(userService.login(requestDto), HttpStatus.OK);
    }

    //Hanshin
/*    @GetMapping("/users")
    public ResponseEntity<String> signup(@RequestBody UserRequestDto requestDto){
        userService.signup(requestDto);
        return new ResponseEntity<>("좋아, 새 일원 이로군!",HttpStatus.OK);
    }
    @PostMapping("/users")
    public ResponseEntity<String>login(@RequestBody UserRequestDto requestDto){
        userService.login(requestDto);
        return new ResponseEntity<>("아 통과!",HttpStatus.OK);
    }*/
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequestDto requestDto) {
        User user = userService.createUser(requestDto);

        return new ResponseEntity<>(new CreateUserResponse(user), HttpStatus.OK);
    }

}
