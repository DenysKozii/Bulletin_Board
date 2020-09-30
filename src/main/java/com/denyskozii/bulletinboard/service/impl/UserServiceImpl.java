package com.denyskozii.bulletinboard.service.impl;

import com.denyskozii.bulletinboard.dto.UserDto;
import com.denyskozii.bulletinboard.exception.EntityNotFoundException;
import com.denyskozii.bulletinboard.model.Role;
import com.denyskozii.bulletinboard.model.User;
import com.denyskozii.bulletinboard.repository.UserRepository;
import com.denyskozii.bulletinboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Validator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, Validator validator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    @Override
    public UserDto getUserById(Long id) {
        return mapToUserDto.apply(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " doesn't exists!")));
    }

    @Override
    public Long getUserIdByName(String email) {
        return userRepository
                .findByEmail(email)
                .getId();
    }

    @Override
    public List<UserDto> getAllByRole(String role) {
        return userRepository.
                getAllByRole(Role.valueOf(role.toUpperCase())).stream().
                map(mapToUserDto).
                collect(Collectors.toList());
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
    @Transactional
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user.getId() != null) {
            return mapToUserDto.apply(user);
        } else {
            throw new EntityNotFoundException("User with email " + email + " doesn't exist");
        }
    }

    @Override
    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email);
        System.out.println("login ---------------------------");
        if (user == null)
            throw new EntityNotFoundException("User with email " + email + " not found");
        return passwordEncoder.matches(password, user.getPassword());
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

