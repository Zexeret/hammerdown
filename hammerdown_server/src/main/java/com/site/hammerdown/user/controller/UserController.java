package com.site.hammerdown.user.controller;

import com.site.hammerdown.common.model.APIResponse;
import com.site.hammerdown.security.services.UserDetailsImpl;
import com.site.hammerdown.user.payload.UserDTO;
import com.site.hammerdown.user.repository.UserRepository;
import com.site.hammerdown.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<APIResponse> getUserDetails(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDTO userDTO = userService.getUserDetailsFromUserName(userDetails.getUsername()) ;

        return APIResponse.success(userDTO)
                .generateResponseEntity();
    }
}
