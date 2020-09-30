package com.denyskozii.bulletinboard.controller;

import com.denyskozii.bulletinboard.dto.UserDto;
import com.denyskozii.bulletinboard.model.Role;
import com.denyskozii.bulletinboard.model.User;
import com.denyskozii.bulletinboard.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Controller
public class LoginController {
    @Autowired
    UserServiceImpl userServiceimpl;

    @PostMapping("/registration")
    public String addUser(@Valid UserDto user, Errors errors, Model model) {
        if(user.getPassword().length()<5){
            model.addAttribute("message", "Password is not strong enough");
            return "registration";
        }
        if (errors.hasErrors()) {
            model.addAttribute("error", true);
            return "registration";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("message", "Check your password confirmation");
            return "registration";
        }
        user.setActive(true);
        user.setRole(Role.USER);
        boolean addUser = userServiceimpl.register(user);
        if (addUser) {
            return "redirect:/form-login";
        }
        model.addAttribute("message", "User already exists!");
        return "login";
    }

    @GetMapping("/registration")
    public String register() {
        return "registration";
    }


//    @PostMapping("/form-login")
//    public String login(@RequestParam String email, @RequestParam String password, Model model) {
//        boolean addUser = userServiceimpl.login(email,password);
//        System.out.println("login------------");
//        System.out.println("login------------");
//        System.out.println("login------------");
//        System.out.println("login------------");
//        System.out.println("login------------");
//        if (addUser) {
//            UserDto user = userServiceimpl.getUserByEmail(email);
//            Long userId = user.getId();
//            model.addAttribute("userId", userId);
//            System.out.println(userId);
//            return "user/account/{userId}";
//        }
//        model.addAttribute("message", "User already exists!");
//        return "login";
//    }
//
//    @GetMapping("/form-login")
//    public String login() {
//        System.out.println("login------------");
//        System.out.println("login------------");
//        System.out.println("login------------");
//        System.out.println("login------------");
//        System.out.println("login------------");
//        return "login";
//    }
}
