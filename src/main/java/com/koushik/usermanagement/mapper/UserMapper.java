package com.koushik.usermanagement.mapper;

import com.koushik.usermanagement.dto.UserRequestDTO;
import com.koushik.usermanagement.dto.UserResponseDTO;
import com.koushik.usermanagement.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO mapToDTO(User user){
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setAge(user.getAge());

        return userResponseDTO;
    }

    public User mapToUser(UserRequestDTO requestDTO){
        User user = new User();

        user.setName(requestDTO.getName());
        user.setAge(requestDTO.getAge());

        return user;
    }
}
