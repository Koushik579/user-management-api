package com.koushik.usermanagement.controller;

import com.koushik.usermanagement.dto.PageResponse;
import com.koushik.usermanagement.dto.UserResponseDTO;
import com.koushik.usermanagement.entity.User;
import com.koushik.usermanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final Logger log = LoggerFactory.getLogger(AdminController.class);
    private final UserService userService;
    public AdminController(UserService userService){
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<PageResponse<UserResponseDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        log.info("Searching for Users");
        return ResponseEntity.ok(userService.getUsers(page,size,sortBy));
    }
}
