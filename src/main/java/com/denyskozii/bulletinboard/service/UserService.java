package com.denyskozii.bulletinboard.service;

import com.denyskozii.bulletinboard.dto.UserDto;
import com.denyskozii.bulletinboard.model.Role;
import com.denyskozii.bulletinboard.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.function.Function;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
public interface UserService extends UserDetailsService {

    /**
     * Update existing user with new data
     * @param userDto
     * @return <code>UserDto</code> user
     */
    UserDto updateUser(UserDto userDto);

    /**
     * Register new user with information
     * @param user
     * @return <code>boolean</code> user
     */
    boolean register(UserDto user);

    /**
     * Function for mapping user to UserDto
     */
    Function<User, UserDto> mapToUserDto = (user -> UserDto.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .password(user.getPassword())
            .confirmPassword(user.getConfirmPassword())
            .role(user.getRole())
            .build());

    /**
     * Function for mapping UserDto to user
     */
    Function<UserDto, User> mapToUser = (userDto -> {
        User user = new User();
        user.setPassword(userDto.getPassword());
        user.setConfirmPassword(userDto.getConfirmPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.USER);
        return user;
    });
}
