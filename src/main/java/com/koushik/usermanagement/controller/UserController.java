package com.koushik.usermanagement.controller;

import com.koushik.usermanagement.dto.AuthResponseDTO;
import com.koushik.usermanagement.dto.LoginRequestDTO;
import com.koushik.usermanagement.dto.UserRequestDTO;
import com.koushik.usermanagement.dto.UserResponseDTO;
import com.koushik.usermanagement.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        log.info("Searching for Users");
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody @Valid LoginRequestDTO dto){
        log.info("Trying login with email : {}", dto.email());
        return ResponseEntity.ok(userService.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> addUsersDb(@RequestBody @Valid UserRequestDTO user){
        log.info("Adding new users");
        UserResponseDTO savedUser = userService.addUser(user);

        return ResponseEntity.status(201).body(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        log.info("Searching users by id : {}", id);
        UserResponseDTO getUser = userService.getUserWithId(id);

        return ResponseEntity.ok(getUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserById(@PathVariable Long id,
                                                          @RequestBody @Valid UserRequestDTO user){
        log.info("Updating user by id : {}", id);
        userService.updateUser(id,user);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        userService.deleteUser(id);
        log.info("Deleting user by id : {}", id);
        return ResponseEntity.noContent().build();
    }

}
