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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Spring Controller that handles users (Account, Edit)
 *
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

    /**
     * Shows information about user by HttpServletRequest
     *
     * @param request
     * @param model
     * @return String
     */
    @GetMapping
    public String account(HttpServletRequest request, Model model) {
        log.info("controller account");
        Object userDto = ((UsernamePasswordAuthenticationToken)request.getUserPrincipal()).getPrincipal();
        model.addAttribute("user", userDto);
        model.addAttribute("bulletin", new BulletinDto());
        log.info("Return information about " + userDto);
        return "user/account";
    }

    /**
     * Updating user with new information
     *
     * @param userDto
     * @param bindingResult
     * @return String
     */
    @PostMapping("/edit")
    public String editUserFormSubmit(@Valid @ModelAttribute("user") UserDto userDto,
                                        BindingResult bindingResult) {
        log.info("controller edit");
        if (bindingResult.hasErrors()) {
            log.error("Error(s) updating user id " + userDto.getId() + ": " + bindingResult.getAllErrors());
            return "user/account";
        }
        userService.updateUser(userDto);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto, userDto.getPassword(), userDto.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Updating user id: " + userDto.getId());
        return "redirect:/user";
    }
}
