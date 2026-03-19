package com.koushik.usermanagement.service;

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

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public User getUserWithId(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public boolean updateUser(Long id,User user){
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
