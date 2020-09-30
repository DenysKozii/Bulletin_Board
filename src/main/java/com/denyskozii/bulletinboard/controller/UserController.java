package com.denyskozii.bulletinboard.controller;


import com.denyskozii.bulletinboard.dto.BulletinDto;
import com.denyskozii.bulletinboard.dto.UserDto;
import com.denyskozii.bulletinboard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Controller
@Slf4j
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String account(HttpServletRequest request, Model model) {
//        Long userId = Long.valueOf(request.getUserPrincipal().getName());
//        log.info("Get user by id " + userId);
//        UserDto userDto = userService.getUserById(userId);
        Object userDto = ((UsernamePasswordAuthenticationToken)request.getUserPrincipal()).getPrincipal();
        model.addAttribute("user", userDto);
        model.addAttribute("bulletin", new BulletinDto());
        return "user/account";
    }

    @GetMapping("/edit")
    public String editUserForm(HttpServletRequest request, Model model) {
        Long userId = Long.valueOf(request.getUserPrincipal().getName());
        log.info("Get user by id " + userId);
        UserDto userDto = userService.getUserById(userId);
        model.addAttribute("user", userDto);
        return "user/edit";
    }


    @PostMapping("/edit")
    public String editUserFormSubmit(@Valid @ModelAttribute("user") UserDto userDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Error(s) updating user id " + userDto.getId() + ": " + bindingResult.getAllErrors());
            return "user/edit";
        }
        userService.updateUser(userDto);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto, userDto.getPassword(), userDto.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Updating user id: " + userDto.getId());
        return "redirect:/user";
    }

    /**
     * test connection.
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
