package com.study.springsecuritystudy.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 유저 id

    @Column(nullable = false, unique = true)
    private String username; // ID

    @Column(nullable = false)
    private String password; // PW

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)// 이넘 사용시 데이터 저장 어노테이션
    private UserRoleEnum role; // 유저 권한 정보

@Builder
    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
