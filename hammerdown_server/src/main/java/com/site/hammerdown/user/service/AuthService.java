package com.site.hammerdown.user.service;

import com.site.hammerdown.user.payload.LoginRequest;
import com.site.hammerdown.user.payload.SignupRequest;
import com.site.hammerdown.user.payload.UserDTO;

public interface AuthService {
    UserDTO authenticateUserFromCookie(LoginRequest loginRequest);

    UserDTO registerUser(SignupRequest signUpRequest);
}
