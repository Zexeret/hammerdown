package com.site.hammerdown.user.service;

import com.site.hammerdown.user.payload.UserDTO;

public interface UserService {
    UserDTO getUserDetailsFromUserName(String username);

    UserDTO updateUserDetails(UserDTO userDTO);
}
