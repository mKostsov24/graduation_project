package org.example.service;

import org.example.model.Users;
import org.example.model.dto.ErrorDTO;
import org.example.model.dto.users.NewPasswordRequestDTO;
import org.example.model.dto.users.NewUserDTO;
import org.example.repository.CaptchaCodeRepository;
import org.example.repository.UserRepository;
import org.example.utils.ErrorsList;
import org.example.utils.mail.EmailServiceImpl;
import org.example.сonfig.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final CaptchaCodeRepository captchaCodeRepository;
    private final SecurityConfig securityConfig;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailServiceImpl;
    private final SettingServiceImpl settingService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CaptchaCodeRepository captchaCodeRepository,
                           SecurityConfig securityConfig, PasswordEncoder passwordEncoder,
                           EmailServiceImpl emailServiceImpl, SettingServiceImpl settingService) {
        this.userRepository = userRepository;
        this.captchaCodeRepository = captchaCodeRepository;
        this.securityConfig = securityConfig;
        this.passwordEncoder = passwordEncoder;
        this.emailServiceImpl = emailServiceImpl;
        this.settingService = settingService;
    }

    @Transactional
    public ResponseEntity<?> registerUser(NewUserDTO user) {
        if (!settingService.getGlobalSettings().isMultiuserMode()) {
            return ResponseEntity.badRequest().body("NOT FOUND");
        }
        Map<String, Object> errors = validateUserInputAndGetErrors(user);
        if (errors.size() > 0) {
            return ResponseEntity.ok(new ErrorDTO(false, errors));
        } else {
            userRepository.save(new Users
                    .Builder()
                    .setName(user.getName())
                    .setEmail(user.getEmail())
                    .setPassword(securityConfig.passwordEncoder().encode(user.getPassword()))
                    .setRegTime(Instant.now()).build());
            Map<String, Boolean> successfully = new HashMap<>();
            successfully.put("result", true);
            return ResponseEntity.ok(successfully);
        }
    }

    private Map<String, Object> validateUserInputAndGetErrors(NewUserDTO user) {
        Map<String, Object> errorsMap = new HashMap<>();
        Users usersFromDB = userRepository.findByEmail(user.getEmail());

        if (usersFromDB != null) {
            errorsMap.put("email", ErrorsList.STRING_AUTH_EMAIL_ALREADY_REGISTERED);
        }
        if (user.getPassword() == null || user.getPassword().length() < ErrorsList.INT_AUTH_MIN_PASSWORD_LENGTH) {
            errorsMap.put("password", ErrorsList.STRING_AUTH_INVALID_PASSWORD_LENGTH);
        }

        if (!user.getCaptcha().equals(user.getCaptchaSecret())) {
            errorsMap.put("captcha", ErrorsList.STRING_AUTH_INVALID_CAPTCHA);
        }

        return errorsMap;
    }

    @Transactional
    public ResponseEntity<?> restorePassword(String email) {

        if (userRepository.findByEmail(email) != null) {

            final String code = UUID.randomUUID().toString();

            userRepository.setCodeValue(code, userRepository.findByEmail(email).getId());


            final String port = "8080";
            final String hostName = "kostsov-java-skillbox.herokuapp.com";
            final String url = String.format(ErrorsList.STRING_AUTH_SERVER_URL, hostName, port);

            emailServiceImpl.sendSimpleMessage(
                    email,
                    ErrorsList.STRING_AUTH_MAIL_SUBJECT,
                    String.format(ErrorsList.STRING_AUTH_MAIL_MESSAGE, url, code)
            );

            return ResponseEntity.ok(new ErrorDTO(true, null));

        } else {
            return ResponseEntity.ok(new ErrorDTO(false, null));
        }
    }

    @Transactional
    public ResponseEntity<?> changePassword(NewPasswordRequestDTO newPasswordRequestDTO) {
        Map<String, Object> errors = validateNewPasswordAndGetErrors(newPasswordRequestDTO);
        if (errors.size() > 0) {
            return ResponseEntity.ok(new ErrorDTO(false, errors));
        } else {
            userRepository.setNewPassword(passwordEncoder.encode(newPasswordRequestDTO.getPassword()),
                    userRepository.getByCode(newPasswordRequestDTO.getCode()).getId());
            userRepository.setCodeNull(userRepository.getByCode(newPasswordRequestDTO.getCode()).getId());
            return ResponseEntity.ok(new ErrorDTO(true, null));
        }
    }

    private Map<String, Object> validateNewPasswordAndGetErrors(NewPasswordRequestDTO newPasswordRequestDTO) {
        Map<String, Object> errorsMap = new HashMap<>();

        if (userRepository.getByCode(newPasswordRequestDTO.getCode()) == null) {
            errorsMap.put("code", ErrorsList.STRING_CODE_IS_OLD);
        }

        if (newPasswordRequestDTO.getCode().length() < ErrorsList.INT_AUTH_MIN_PASSWORD_LENGTH) {
            errorsMap.put("password", ErrorsList.STRING_PASSWORD_SHORT);
        }

        if (captchaCodeRepository.findBySecretCode(newPasswordRequestDTO.getCaptchaSecret()) != null
                && captchaCodeRepository.findBySecretCode(newPasswordRequestDTO.getCaptchaSecret())
                .getCode().equals(newPasswordRequestDTO.getCode())) {
            errorsMap.put("captcha", ErrorsList.STRING_CAPTCHA_INVALID);
        }
        return errorsMap;
    }
}
