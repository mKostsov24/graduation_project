package org.example.controller;

import org.example.utils.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/api/image")
public class ApiImageController {

    private final ImageService imageService;

    @Autowired
    public ApiImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image, HttpServletRequest request) {

        ResponseEntity<?> response = imageService.store(image, request);
        if(response.getStatusCode().value() != 200){
            return ResponseEntity.ok(Objects.requireNonNull(response.getBody()));
        }
            return response;

    }
}
