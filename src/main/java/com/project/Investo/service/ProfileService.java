package com.project.Investo.service.impl;

import com.project.Investo.dto.ProfileDTO;
import com.project.Investo.dto.UpdateProfileDTO;
import com.project.Investo.security.entity.User;
import com.project.Investo.security.repository.UserRepository;
import com.project.Investo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final UserService userService;
    private final UserRepository userRepository;

    public ProfileService(UserService userService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public ProfileDTO getProfile() {

        User user = userService.getCurrentUser();

        return ProfileDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .mobileVerified(user.isMobileVerified())
                .build();
    }

    public ProfileDTO updateProfile(UpdateProfileDTO request) {

        User user = userService.getCurrentUser();

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getMobileNumber() != null) {
            user.setMobileNumber(request.getMobileNumber());
            user.setMobileVerified(false); // reset verification
        }

        userRepository.save(user);

        return getProfile();
    }
}