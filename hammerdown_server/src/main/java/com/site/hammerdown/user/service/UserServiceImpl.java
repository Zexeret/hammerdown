package com.site.hammerdown.user.service;

import com.site.hammerdown.common.exceptions.APIException;
import com.site.hammerdown.user.model.User;
import com.site.hammerdown.user.payload.UserDTO;
import com.site.hammerdown.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public UserDTO getUserDetailsFromUserName(String username) {
        User savedUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new APIException("User is not found."));

        return UserDTO.createFromUser(savedUser);
    }
}
