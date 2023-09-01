package com.sparta.springlevel3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) // Enum type를 DB에 저장
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role) { // 아이디는 자동증가임
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
