package com.site.hammerdown.security.response;

import com.site.hammerdown.model.Role;
import com.site.hammerdown.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class UserInfoResponse {
    private Long id;
    private String username;
    private List<String> roles;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;

    public static UserInfoResponse getFromUser(User user) {
        Set<Role> userRoles = user.getRoles();
        List<String> userRolesString = userRoles.stream()
                .map(role -> role.getRoleName().name())
                .toList() ;

        return builder()
                .id(user.getUserId())
                .username(user.getUserName())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(userRolesString)
                .build();
    }
}


