package com.denyskozii.bulletinboard.service;


import com.denyskozii.bulletinboard.dto.UserDto;
import com.denyskozii.bulletinboard.model.Role;
import com.denyskozii.bulletinboard.model.User;

import java.util.List;
import java.util.function.Function;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
public interface UserService  {

    UserDto getUserById(Long id);

    Long getUserIdByName(String userFullName);

    List<UserDto> getAllByRole(String role);

    UserDto updateUser(UserDto userDto);

    UserDto getUserByEmail(String email);


    boolean login(String email, String password);


    boolean register(UserDto user);

    Function<User, UserDto> mapToUserDto = (user -> UserDto.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .password(user.getPassword())
            .confirmPassword(user.getConfirmPassword())
            .role(user.getRole())
            .active(user.isActive())
            .build());

    Function<UserDto, User> mapToUser = (userDto -> {
        User user = new User();
        user.setPassword(userDto.getPassword());
        user.setConfirmPassword(userDto.getConfirmPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.USER);
        user.setActive(userDto.isActive());
        return user;
    });

}
