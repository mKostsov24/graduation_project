package org.example.controller;

import org.example.model.dto.NewProfileDTO;
import org.example.service.ProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/api/profile")
public class ApiProfileController {

    private final ProfileServiceImpl profileService;

    @Autowired
    public ApiProfileController(ProfileServiceImpl profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> editProfile(@ModelAttribute NewProfileDTO newProfileDTO, Principal principal, HttpServletRequest request) {
        return profileService.updateUserProfile(principal, newProfileDTO, request);
    }
}
