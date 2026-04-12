package com.project.Investo.service.impl;



import com.project.Investo.security.config.SecurityUtils;
import com.project.Investo.security.entity.User;
import com.project.Investo.security.repository.UserRepository;
import com.project.Investo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {

        String email = SecurityUtils.getCurrentUserEmail();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}