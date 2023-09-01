package com.sparta.springlevel3.controller;

import com.sparta.springlevel3.dto.LoginDto;
import com.sparta.springlevel3.dto.SignUpDto;
import com.sparta.springlevel3.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 그냥 컨트롤러라 return값 뒤에 html 붙음
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpDto requestDto) {

        return ResponseEntity.ok(userService.signup(requestDto));

    }

    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody LoginDto requestDto, HttpServletResponse res) {
        return ResponseEntity.ok(userService.login(requestDto, res));
    }
}
