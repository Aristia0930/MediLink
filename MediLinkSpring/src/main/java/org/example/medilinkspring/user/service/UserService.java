package org.example.medilinkspring.user.service;

import org.example.medilinkspring.user.entity.User;
import org.example.medilinkspring.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean idCheck(String username){
        User user=userRepository.findByName(username);
        System.out.println(user);
        if (user==null){

            return true;
        }
        else {
            return false;
        }
    }

}
