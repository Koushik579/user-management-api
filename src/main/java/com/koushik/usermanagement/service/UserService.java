package com.koushik.usermanagement.service;

import com.koushik.usermanagement.dto.UserRequestDTO;
import com.koushik.usermanagement.dto.UserResponseDTO;
import com.koushik.usermanagement.entity.User;
import com.koushik.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
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
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public UserResponseDTO addUser(UserRequestDTO user){
        User newUser = mapToUser(user);
        User savedUser = userRepository.save(newUser);
        return mapToDTO(savedUser);
    }

    public UserResponseDTO getUserWithId(Long id){

        return userRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    public boolean updateUser(Long id, UserRequestDTO user){
        Optional<User> existing = userRepository.findById(id);

        if(existing.isEmpty()){
            return false;
        }

        User dbUser = existing.get();
        dbUser.setName(user.getName());
        dbUser.setAge(user.getAge());

        userRepository.save(dbUser);
        return true;
    }

    public boolean deleteUser(Long id){
        Optional<User> u = userRepository.findById(id);
        if(u.isEmpty()){
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

}
