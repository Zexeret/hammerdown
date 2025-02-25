package com.site.hammerdown.user.service;

import com.site.hammerdown.common.exceptions.APIException;
import com.site.hammerdown.common.model.AppRole;
import com.site.hammerdown.common.model.Role;
import com.site.hammerdown.common.repository.RoleRepository;
import com.site.hammerdown.security.services.UserDetailsImpl;
import com.site.hammerdown.user.model.User;
import com.site.hammerdown.user.payload.LoginRequest;
import com.site.hammerdown.user.payload.SignupRequest;
import com.site.hammerdown.user.payload.UserDTO;
import com.site.hammerdown.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDTO authenticateUserFromCookie(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            throw new APIException("Bad credentials", HttpStatus.NOT_FOUND.value());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .toList();

        return UserDTO.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }

    @Override
    public UserDTO registerUser(SignupRequest signUpRequest) {
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

        return UserDTO.createFromUser(savedUser);
    }

}
