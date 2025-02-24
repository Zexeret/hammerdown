package com.site.hammerdown.controller;

import com.site.hammerdown.exceptions.APIException;
import com.site.hammerdown.model.APIResponseStatus;
import com.site.hammerdown.model.AppRole;
import com.site.hammerdown.model.Role;
import com.site.hammerdown.model.User;
import com.site.hammerdown.payload.APIResponse;
import com.site.hammerdown.repository.RoleRepository;
import com.site.hammerdown.repository.UserRepository;
import com.site.hammerdown.security.jwt.JwtUtils;
import com.site.hammerdown.security.request.LoginRequest;
import com.site.hammerdown.security.request.SignupRequest;
import com.site.hammerdown.security.response.UserInfoResponse;
import com.site.hammerdown.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @PostMapping("/auth/signin")
    public ResponseEntity<APIResponse> authenticateUserFromCookie(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
           throw new APIException("Bad credentials", HttpStatus.NOT_FOUND.value());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookieFromUserDetails(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .toList();

        UserInfoResponse userInfo = UserInfoResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .roles(roles)
                .build();

        APIResponse response = APIResponse.builder()
                .status(APIResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .data(userInfo)
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
    }


    @PostMapping("/auth/signup")
    public ResponseEntity<APIResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUsername())) {
            throw new APIException("Username is already taken");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new APIException("Email is already in use!");
        }

        // Create new user's account
        User user = User.builder()
                .userName(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        Set<AppRole> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName((AppRole.ROLE_USER))
                    .orElseThrow(() -> new APIException("Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                        Role requestedRole = roleRepository.findByRoleName(role)
                                .orElseThrow(() -> new APIException("Unknown role encountered in user registration for username " + signUpRequest.getUsername()));
                        roles.add(requestedRole);
            });
        }

        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookieFromUserName(signUpRequest.getUsername());
        UserInfoResponse userInfo = UserInfoResponse.getFromUser(savedUser);
        APIResponse response = APIResponse.success(userInfo);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
    }


    @GetMapping("/user")
    public ResponseEntity<APIResponse> getUserDetails(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String username = userDetails.getUsername();

        User savedUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new APIException("User is not found."));
        UserInfoResponse userInfoResponse = UserInfoResponse.getFromUser(savedUser);
        APIResponse response = APIResponse.success(userInfoResponse);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/auth/signout")
    public ResponseEntity<APIResponse> signoutUser(){
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                        cookie.toString())
                .body(APIResponse.success("You have been signed out"));
    }
}
