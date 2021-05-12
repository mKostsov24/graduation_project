package org.example.controller;

import org.example.model.dto.LoginRequest;
import org.example.model.dto.users.NewPasswordRequestDTO;
import org.example.model.dto.users.NewUserDTO;
import org.example.model.dto.users.RestorePasswordDTO;
import org.example.response.LogOutResponse;
import org.example.response.LoginResponse;
import org.example.service.AuthService;
import org.example.service.CaptchaCodeServiceImpl;
import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthenticationManager authenticationManager;
    private final CaptchaCodeServiceImpl captchaCodeService;
    private final AuthService authService;
    private final UserServiceImpl userService;

    @Autowired
    public ApiAuthController(CaptchaCodeServiceImpl captchaCodeService, AuthService authService,
                             UserServiceImpl userService, AuthenticationManager authenticationManager) {
        this.captchaCodeService = captchaCodeService;
        this.authService = authService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
            User user = (User) auth.getPrincipal();
            return ResponseEntity.ok(authService.getLoginResponse(user.getUsername()));
        } catch (Exception exception){
            return ResponseEntity.ok(new LoginResponse(false, null));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<LogOutResponse> logout(HttpServletRequest request,
                                                 HttpServletResponse response) {
        authService.getLogOut(request, response);
        return ResponseEntity.ok(new LogOutResponse());
    }

    @GetMapping("/check")
    private ResponseEntity<LoginResponse> chek(Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok(new LoginResponse());
        }
        return ResponseEntity.ok(authService.getLoginResponse(principal.getName()));
    }

    @GetMapping("/captcha")
    public ResponseEntity<?> getCaptcha() {
        return captchaCodeService.getCaptcha();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody NewUserDTO user) {
        return userService.registerUser(user);
    }

    @PostMapping("/restore")
    public ResponseEntity<?> registerUser(@RequestBody RestorePasswordDTO email) {
        return userService.restorePassword(email.getEmail());
    }

    @PostMapping("/password")
    public ResponseEntity<?> registerUser(@RequestBody NewPasswordRequestDTO passwordRequestDTO) {
        return userService.changePassword(passwordRequestDTO);
    }

}
