package com.site.hammerdown.user.payload;

import com.site.hammerdown.common.model.Role;
import com.site.hammerdown.user.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;

    @NotBlank
    private String username;
    private List<String> roles;

    @NotBlank
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


