package com.project.Investo.controller;

import com.project.Investo.dto.ProfileDTO;
import com.project.Investo.dto.UpdateProfileDTO;

import com.project.Investo.service.ProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ProfileDTO getProfile() {
        return profileService.getProfile();
    }

    @PutMapping
    public ProfileDTO updateProfile(@RequestBody UpdateProfileDTO request) {
        return profileService.updateProfile(request);
    }
}