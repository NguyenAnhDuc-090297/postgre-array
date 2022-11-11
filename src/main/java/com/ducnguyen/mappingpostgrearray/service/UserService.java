package com.ducnguyen.mappingpostgrearray.service;

import com.ducnguyen.mappingpostgrearray.entity.User;
import com.ducnguyen.mappingpostgrearray.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getOne(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
