package com.denyskozii.bulletinboard.service.impl;

import com.denyskozii.bulletinboard.dto.UserDto;
import com.denyskozii.bulletinboard.exception.EntityNotFoundException;
import com.denyskozii.bulletinboard.model.User;
import com.denyskozii.bulletinboard.repository.UserRepository;
import com.denyskozii.bulletinboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Validator;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Validator validator) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(8);
        this.validator = validator;
    }


    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userDto.getId() + " doesn't exists!"));
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(userDto.getRole());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setConfirmPassword(userDto.getConfirmPassword());
        userRepository.save(user);
        return userDto;
    }

    @Override
    public boolean register(UserDto user) {
        User userEntity = userRepository.findByEmail(user.getEmail());
        if (userEntity != null || validator.validate(user).size() != 0)
            return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(mapToUser.apply(user));
        return true;
    }

    @Override
    public UserDto loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return mapToUserDto.apply(user);
    }
}

