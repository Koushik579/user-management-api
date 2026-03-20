package com.koushik.usermanagement.controller;

import com.koushik.usermanagement.dto.UserRequestDTO;
import com.koushik.usermanagement.dto.UserResponseDTO;
import com.koushik.usermanagement.entity.User;
import com.koushik.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> addUsersDb(@RequestBody UserRequestDTO user){
        UserResponseDTO savedUser = userService.addUser(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        UserResponseDTO getUser = userService.getUserWithId(id);
        if(getUser == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(getUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserById(@PathVariable Long id,
                                                          @RequestBody UserRequestDTO user){
        boolean checkUser = userService.updateUser(id,user);
        if(!checkUser){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        if (!userService.deleteUser(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
