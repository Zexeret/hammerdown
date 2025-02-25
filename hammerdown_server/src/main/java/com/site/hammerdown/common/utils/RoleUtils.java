package com.site.hammerdown.common.utils;

import com.site.hammerdown.common.exceptions.ResourceNotFoundException;
import com.site.hammerdown.common.model.AppRole;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class RoleUtils {
    public static AppRole stringToAppRole(String string) {
        return switch (string) {
            case "ROLE_USER" -> AppRole.ROLE_USER;
            case "ROLE_ADMIN" -> AppRole.ROLE_ADMIN;
            case "ROLE_SUPERADMIN" -> AppRole.ROLE_SUPERADMIN;
            default -> throw new ResourceNotFoundException("Role", "value", string);
        };
    }

    public static boolean isAdmin(List<String> roles) {
        for(String role : roles) {
            if(role.equals(AppRole.ROLE_ADMIN.name())) {return true;}
        }
        return false;
    }

    public static boolean isUser(List<String> roles) {
        for(String role : roles) {
            if(role.equals(AppRole.ROLE_USER.name())) {return true;}
        }
        return false;
    }

    public static boolean isSuperAdmin(List<String> roles) {
        for(String role : roles) {
            if(role.equals(AppRole.ROLE_SUPERADMIN.name())) {return true;}
        }
        return false;
    }

    public static List<String> convertUserAuthoritiesToString(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(Object::toString)
                .toList() ;
    }
}
