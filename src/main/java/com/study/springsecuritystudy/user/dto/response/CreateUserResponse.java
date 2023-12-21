package com.study.springsecuritystudy.user.dto.response;

import com.study.springsecuritystudy.user.entity.*;
import lombok.*;

@Getter
public class CreateUserResponse {

    Long id;
    String username;
    String password;

    public CreateUserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
