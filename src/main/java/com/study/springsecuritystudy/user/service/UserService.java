package com.study.springsecuritystudy.user.service;

import com.study.springsecuritystudy.security.*;
import com.study.springsecuritystudy.user.dto.*;
import com.study.springsecuritystudy.user.entity.*;
import com.study.springsecuritystudy.user.repository.*;
import jakarta.persistence.*;
import java.util.*;
import lombok.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@Service
@AllArgsConstructor
public class UserService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final JwtUtil jwtUtil;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void signup(UserRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        Optional<User> checkUser = userRepository.findByUsername(username);
        if (checkUser.isPresent()) {
            throw new IllegalArgumentException("저런~ 자넨 이미 등록되 있었구먼!");
        }
        UserRoleEnum role = UserRoleEnum.USER;
        User user = new User(username, password, role);
        userRepository.save(user);
    }

    //베이직 강의 따라잡기
    public void login(UserRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("어허~ 우리 일원이 아닌 것 같은데?"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("뭐야, 암호가 틀리구만!");
        }

    }

    public User createUser(UserRequestDto requestDto) {
        User user = new User(requestDto.getUsername(), requestDto.getPassword(), UserRoleEnum.USER);
        return userRepository.save(user);
    }

    public String login(UserRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
            .orElseThrow(EntityNotFoundException::new);
        if (user.getPassword().equals(requestDto.getPassword())) {
            //암호가 일치하면 JWT지금
            jwtUtil.createToken(user.getUsername(), user.getRole());
        }
        //응 돌아가
        return "돌아가";
    }
}
