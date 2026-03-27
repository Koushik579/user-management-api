package com.koushik.usermanagement.service;

import com.koushik.usermanagement.dto.UserRequestDTO;
import com.koushik.usermanagement.dto.UserResponseDTO;
import com.koushik.usermanagement.entity.User;
import com.koushik.usermanagement.exception.UserNotFoundException;
import com.koushik.usermanagement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserResponseDTO mapToDTO(User user){
        UserResponseDTO resDTO = new UserResponseDTO();
        resDTO.setId(user.getId());
        resDTO.setName(user.getName());
        resDTO.setAge(user.getAge());
        return resDTO;
    }

    private User mapToUser(UserRequestDTO user){
        User resDTO = new User();
        resDTO.setName(user.getName());
        resDTO.setAge(user.getAge());
        return resDTO;
    }

    public List<UserResponseDTO> getUsers(){
        log.info("Featching all users");
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public UserResponseDTO addUser(UserRequestDTO user){
        User newUser = mapToUser(user);
        User savedUser = userRepository.save(newUser);
        log.info("User added with id : {}, Name : {}, Age : {}"
                ,savedUser.getId(),savedUser.getName(),savedUser.getAge());
        return mapToDTO(savedUser);
    }

    public UserResponseDTO getUserWithId(Long id){
        log.info("User searched with id : {}", id);
        return userRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(()-> new UserNotFoundException("Cannot find the user with id : "+id));

    }

    public void updateUser(Long id, UserRequestDTO user){
        User dbUser = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Cannot find the user with id: "+id));

        dbUser.setName(user.getName());
        dbUser.setAge(user.getAge());

        userRepository.save(dbUser);
        log.info("User updated with id : {}, Name : {}, Age : {}", id,dbUser.getName(),dbUser.getAge());
    }

    public void deleteUser(Long id){
        userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Cannot find the user with id:" +id));
        userRepository.deleteById(id);
        log.info("User deleted with \nid : {}", id);
    }

}
