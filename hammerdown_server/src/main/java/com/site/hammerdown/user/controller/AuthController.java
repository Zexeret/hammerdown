package com.site.hammerdown.user.controller;

import com.site.hammerdown.common.model.APIResponse;
import com.site.hammerdown.security.jwt.JwtUtils;
import com.site.hammerdown.user.payload.LoginRequest;
import com.site.hammerdown.user.payload.SignupRequest;
import com.site.hammerdown.user.payload.UserDTO;
import com.site.hammerdown.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtils jwtUtils;
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<APIResponse> authenticateUserFromCookie(@Valid @RequestBody LoginRequest loginRequest) {
        UserDTO userInfo = authService.authenticateUserFromCookie(loginRequest);
        return createResponseAndSetCookieFromUser(userInfo);
    }


    @PostMapping("/signup")
    public ResponseEntity<APIResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        UserDTO userInfo = authService.registerUser(signUpRequest);
        return createResponseAndSetCookieFromUser(userInfo);
    }

    @PostMapping("/signout")
    public ResponseEntity<APIResponse> signoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                        cookie.toString())
                .body(APIResponse.success("You have been signed out"));
    }

    private ResponseEntity<APIResponse> createResponseAndSetCookieFromUser(UserDTO user) {
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookieFromUserName(user.getUsername());
        APIResponse response = APIResponse.success(user);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
    }
}
