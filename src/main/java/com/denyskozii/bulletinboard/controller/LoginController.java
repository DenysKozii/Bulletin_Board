package com.denyskozii.bulletinboard.controller;

import com.denyskozii.bulletinboard.dto.UserDto;
import com.denyskozii.bulletinboard.model.Role;
import com.denyskozii.bulletinboard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Controller
@Slf4j
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public String addUser(@Valid UserDto user, Errors errors, Model model) {
        log.info("Register user " + user);
        if(user.getPassword().length()<5){
            log.warn("Password is not strong enough");
            model.addAttribute("message", "Password is not strong enough");
            return "registration";
        }
        if (errors.hasErrors()) {
            log.error(errors.toString());
            model.addAttribute("error", true);
            return "registration";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            log.warn("Check your password confirmation");
            model.addAttribute("message", "Check your password confirmation");
            return "registration";
        }
        user.setRole(Role.USER);
        boolean addUser = userService.register(user);
        if (addUser) {
            log.warn(String.format("Register user %s successfully!",user));
            return "redirect:/form-login";
        }
        log.error(String.format("User %s already exists!",user));
        model.addAttribute("message", "User already exists!");
        return "login";
    }

    @GetMapping("/registration")
    public String register() {
        return "registration";
    }
}
