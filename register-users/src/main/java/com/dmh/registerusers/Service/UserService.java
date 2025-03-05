package com.dmh.registerusers.Service;

import com.dmh.registerusers.Repository.UserRepository;
import com.dmh.registerusers.UserDTO.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }
}
