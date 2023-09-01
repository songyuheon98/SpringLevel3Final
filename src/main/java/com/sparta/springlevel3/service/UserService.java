package com.sparta.springlevel3.service;

import com.sparta.springlevel3.dto.LoginDto;
import com.sparta.springlevel3.dto.SignUpDto;
import com.sparta.springlevel3.entity.User;
import com.sparta.springlevel3.entity.UserRoleEnum;
import com.sparta.springlevel3.jwt.JwtUtil;
import com.sparta.springlevel3.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    // ADMIN_TOKEN 일반 사용자인지 관리자인지
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9]{4,10}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9]{8,15}$");

    public static boolean isValidUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isValidPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public void signup(SignUpDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        Boolean isAdmin = requestDto.getIsAdmin();
        String adminToken = requestDto.getAdminToken();

        if (!isValidPassword(password))
            throw new IllegalArgumentException("PW 형태가 부적절합니다.");
        else
            password = passwordEncoder.encode(password);

        if (!isValidUsername(username))
            throw new IllegalArgumentException("ID 형태가 부적절합니다.");

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username); // 쿼리 메서드 사용
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER; // 일단 일반 사용자 권한 넣어 놓음
        if (isAdmin == true) {
            if (!ADMIN_TOKEN.equals(adminToken)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);

        System.out.println("=======");
    }

    public void login(LoginDto requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return "{\"message\": \"등록된 사용자가 없습니다\", \"statusCode\": 400}";
        }


        if (!passwordEncoder.matches(password,user.getPassword()))
            return "{\"message\": \"PW가 일치하지 않습니다.\", \"statusCode\": 400}";


        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);

    }

}
