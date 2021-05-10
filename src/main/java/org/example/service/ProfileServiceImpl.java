package org.example.service;

import org.example.model.Users;
import org.example.model.dto.ErrorDTO;
import org.example.model.dto.NewProfileDTO;
import org.example.repository.UserRepository;
import org.example.utils.ErrorsList;
import org.example.utils.ImageService;
import org.example.сonfig.SecurityConfig;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ProfileServiceImpl {

    private final UserRepository usersRepository;
    private final SecurityConfig securityConfig;
    private final ImageService imageService;

    @Autowired
    public ProfileServiceImpl(UserRepository usersRepository, SecurityConfig securityConfig, ImageService imageService) {
        this.usersRepository = usersRepository;
        this.securityConfig = securityConfig;
        this.imageService = imageService;
    }

    public ResponseEntity<?> updateUserProfile(Principal email, NewProfileDTO newProfileDTO, HttpServletRequest request) {
        Users currentUser = usersRepository.findByEmail(email.getName());
        Map<String, Object> errors = validateNewProfileInputAndGetErrors(newProfileDTO, currentUser);
        if (errors.size() > 0) {
            return ResponseEntity.badRequest().body(new ErrorDTO(false, errors));
        } else {
            if (newProfileDTO.getName() != null && !currentUser.getName().equals(newProfileDTO.getName())) {
                usersRepository.setNewName(newProfileDTO.getName(), currentUser.getId());
            }
            if (newProfileDTO.getEmail() != null && !currentUser.getEmail().equals(newProfileDTO.getEmail())) {
                usersRepository.setNewEmail(newProfileDTO.getEmail(), currentUser.getId());
            }

            if (newProfileDTO.getPassword() != null) {
                usersRepository.setNewPassword(securityConfig.passwordEncoder().encode(newProfileDTO.getPassword()), currentUser.getId());
            }

            if (!newProfileDTO.isRemovePhoto()) {
                System.out.println(currentUser.getId());
                usersRepository.deletePhoto(currentUser.getId());
            }

            if (newProfileDTO.getPhoto() != null) {

                MultipartFile file = newProfileDTO.getPhoto();
                String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                Map<String, Object> imageMap = imageService.validateFile(file, filename);
                if (imageMap.size() > 0) {
                    return ResponseEntity.badRequest().body(new ErrorDTO(false, imageMap));
                }
                imageService.store(newProfileDTO.getPhoto(), request);
                resize(file, imageService.getPath());
                usersRepository.setNewPhoto(imageService.getResult(), currentUser.getId());

            }
            return ResponseEntity.ok(new ErrorDTO(true, null));
        }
    }

    private Map<String, Object> validateNewProfileInputAndGetErrors(NewProfileDTO newProfileDTO, Users currentUser) {
        Map<String, Object> errorsMap = new HashMap<>();
        Users usersFromDB = usersRepository.findByEmail(newProfileDTO.getEmail());

        if (usersFromDB != null && !currentUser.getEmail().equals(usersFromDB.getEmail())) {
            errorsMap.put("email", ErrorsList.STRING_AUTH_EMAIL_ALREADY_REGISTERED);
        }

        if (newProfileDTO.getPassword() != null && (newProfileDTO.getPassword().length() >= 6 ||
                newProfileDTO.getPassword().length() <= 20)) {
            errorsMap.put("password", ErrorsList.STRING_AUTH_INVALID_PASSWORD_LENGTH);
        }

        if (newProfileDTO.getName() != null && newProfileDTO.getName().length() > 255) {
            errorsMap.put("name", ErrorsList.STRING_AUTH_WRONG_NAME);
        }

        if (currentUser.getPassword().equals(newProfileDTO.getPassword())) {
            errorsMap.put("password", "Вы вввели старый пароль");
        }
        return errorsMap;
    }

    private void resize(MultipartFile file, String pathName) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            BufferedImage newImage = Scalr.resize(image, 35);
            File newFile = new File(pathName);
            ImageIO.write(newImage, "png", newFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

