package com.koushik.usermanagement.mapper;

import com.koushik.usermanagement.dto.UserRequestDTO;
import com.koushik.usermanagement.dto.UserResponseDTO;
import com.koushik.usermanagement.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO mapToDTO(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getEmail(),
                user.getRole()
        );
    }

    public User mapToUser(UserRequestDTO requestDTO){
        User user = new User();

        user.setName(requestDTO.name());
        user.setAge(requestDTO.age());
        user.setEmail(requestDTO.email());
        user.setRole(requestDTO.role().toUpperCase());

        return user;
    }
}
