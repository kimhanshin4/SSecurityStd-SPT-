package com.study.springsecuritystudy.user.dto;

import com.study.springsecuritystudy.user.entity.*;
import lombok.*;

@Getter
public class UserRequestDto {
    private String username;
    private String password;
    private UserRoleEnum role;

}
