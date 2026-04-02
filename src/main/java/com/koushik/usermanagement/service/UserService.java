package com.koushik.usermanagement.service;

import com.koushik.usermanagement.dto.LoginRequestDTO;
import com.koushik.usermanagement.dto.UserRequestDTO;
import com.koushik.usermanagement.dto.UserResponseDTO;
import com.koushik.usermanagement.entity.User;
import com.koushik.usermanagement.exception.InvalidCredentialsException;
import com.koushik.usermanagement.exception.UserNotFoundException;
import com.koushik.usermanagement.mapper.UserMapper;
import com.koushik.usermanagement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository
            , UserMapper userMapper
            , BCryptPasswordEncoder passwordEncoder
            , JwtService jwtService) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    public List<UserResponseDTO> getUsers(){
        log.info("Featching all users");
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapToDTO)
                .toList();
    }

    public UserResponseDTO addUser(UserRequestDTO dto){
        String hashedPass = passwordEncoder.encode(dto.getPassword());
        User newUser = userMapper.mapToUser(dto);
        newUser.setPassword(hashedPass);
        User savedUser = userRepository.save(newUser);
        log.info("User added with id : {}, Name : {}, Age : {}"
                ,savedUser.getId(),savedUser.getName(),savedUser.getAge());
        return userMapper.mapToDTO(savedUser);
    }

    public UserResponseDTO getUserWithId(Long id){
        log.info("User searched with id : {}", id);
        return userRepository.findById(id)
                .map(userMapper::mapToDTO)
                .orElseThrow(()-> new UserNotFoundException("Cannot find the user with id : "+id));

    }

    public void updateUser(Long id, UserRequestDTO dto){
        User dbUser = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Cannot find the dto with id: "+id));

        dbUser.setName(dto.getName());
        dbUser.setAge(dto.getAge());

        userRepository.save(dbUser);
        log.info("User updated with id : {}, Name : {}, Age : {}", id,dbUser.getName(),dbUser.getAge());
    }

    public void deleteUser(Long id){
        userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Cannot find the user with id:" +id));
        userRepository.deleteById(id);
        log.info("User deleted with id : {}", id);
    }

    public String login(LoginRequestDTO dto){
        log.info("Fetching user by Email id : {}", dto.getEmail());
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()-> new InvalidCredentialsException("Invalid Credentials"));
        log.info("User found with the email");
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid Credentials");
        }
        log.info("Password Validated user authorised");
        return jwtService.generateToken(dto.getEmail() );
    }
}
