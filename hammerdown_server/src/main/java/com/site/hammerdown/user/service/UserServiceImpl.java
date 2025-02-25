package com.site.hammerdown.user.service;

import com.site.hammerdown.common.utils.RoleUtils;
import com.site.hammerdown.common.exceptions.APIException;
import com.site.hammerdown.common.exceptions.ResourceNotFoundException;
import com.site.hammerdown.common.model.AppRole;
import com.site.hammerdown.common.model.Role;
import com.site.hammerdown.common.repository.RoleRepository;
import com.site.hammerdown.user.model.User;
import com.site.hammerdown.user.payload.UserDTO;
import com.site.hammerdown.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDTO getUserDetailsFromUserName(String username) {
        User savedUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new APIException("User is not found."));

        return UserDTO.createFromUser(savedUser);
    }

    @Override
    public UserDTO updateUserDetails(UserDTO userDTO) {
        List<String> userDTORoles = userDTO.getRoles();
        Set<Role> userRoles = new HashSet<>();

        userDTORoles.forEach(roleName -> {
            AppRole appRole = RoleUtils.stringToAppRole(roleName) ;
            Role validRole = roleRepository.findByRoleName(appRole)
                    .orElseThrow(()->new APIException("Role "+roleName+" not found."));
            userRoles.add(validRole);
        });

        User user = userRepository.findByUserName(userDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", userDTO.getUsername()));

        User updatedUser = user.updateFromDTO(userDTO,userRoles) ;
        userRepository.save(updatedUser);
        return userDTO;
    }
}
