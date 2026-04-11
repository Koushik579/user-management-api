package com.koushik.usermanagement.controller;

import com.koushik.usermanagement.dto.AuthResponseDTO;
import com.koushik.usermanagement.dto.LoginRequestDTO;
import com.koushik.usermanagement.dto.UserRequestDTO;
import com.koushik.usermanagement.dto.UserResponseDTO;
import com.koushik.usermanagement.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody @Valid LoginRequestDTO dto, HttpServletResponse res){
        log.info("Trying login with email : {}", dto.email());
        return ResponseEntity.ok(userService.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> addUsersDb(@RequestBody @Valid UserRequestDTO user){
        log.info("Adding new users");
        UserResponseDTO savedUser = userService.addUser(user);

        return ResponseEntity.status(201).body(savedUser);
    }
}
