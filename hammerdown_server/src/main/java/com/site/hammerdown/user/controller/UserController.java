package com.site.hammerdown.user.controller;

import com.site.hammerdown.common.utils.RoleUtils;
import com.site.hammerdown.common.exceptions.APIException;
import com.site.hammerdown.common.model.APIResponse;
import com.site.hammerdown.security.services.UserDetailsImpl;
import com.site.hammerdown.user.payload.UserDTO;
import com.site.hammerdown.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<APIResponse> getUserDetails(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDTO userDTO = userService.getUserDetailsFromUserName(userDetails.getUsername());

        return APIResponse.success(userDTO)
                .generateResponseEntity();
    }

    @PutMapping("/user")
    public ResponseEntity<APIResponse> updateUserDetails(Authentication authentication,
                                                         @Valid @RequestBody UserDTO userDTO) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if(!(userDetails.getUsername().equals(userDTO.getUsername()))) {
            List<String> selfRoles = RoleUtils.convertUserAuthoritiesToString(userDetails) ;
            List<String> targetUserRole = RoleUtils.convertUserAuthoritiesToString(userDetails) ;

            if(!isUserAllowedToEditTargetProfile(selfRoles, targetUserRole)) {
                throw new APIException("You don't have permission to update this user");
            }
        }

        UserDTO savedUserDTO = userService.updateUserDetails(userDTO);

        return APIResponse.success(savedUserDTO)
                .generateResponseEntity();
    }

    private boolean isUserAllowedToEditTargetProfile(List<String> selfRoles, List<String> targetUserRole) {
        if(RoleUtils.isSuperAdmin(selfRoles)) {
            return true;
        }else if(RoleUtils.isAdmin(selfRoles)) {
            return RoleUtils.isUser(targetUserRole);
        }
        return false;
    }

}
