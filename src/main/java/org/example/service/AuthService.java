package org.example.service;

import org.example.model.Users;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.example.response.LoginResponse;
import org.example.response.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public AuthService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public LoginResponse getLoginResponse(String email) {
        Users currentUser =
                userRepository.findByEmailOption(email).orElseThrow(() -> new UsernameNotFoundException(email));

        UserLoginResponse userResponse = new UserLoginResponse();
        if (currentUser.isModerator()) {
            userResponse.setSettings(true);
            userResponse.setModerationCount(postRepository.getCountOfNotModeratedPosts());
        } else {
            userResponse.setModerationCount(0);
        }
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setName(currentUser.getName());
        userResponse.setModeration(currentUser.isModerator());
        userResponse.setId(currentUser.getId());
        userResponse.setPhoto(currentUser.getPhoto());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userResponse);
        return loginResponse;
    }

    public void getLogOut(HttpServletRequest request,
                          HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }

}
