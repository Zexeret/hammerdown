package com.site.hammerdown.user.payload;

import com.site.hammerdown.common.model.Role;
import com.site.hammerdown.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private List<String> roles;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;

    public static UserDTO createFromUser(User user) {
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


