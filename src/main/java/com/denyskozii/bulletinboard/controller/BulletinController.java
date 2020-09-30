package com.denyskozii.bulletinboard.controller;

import com.denyskozii.bulletinboard.dto.BulletinDto;
import com.denyskozii.bulletinboard.dto.UserDto;
import com.denyskozii.bulletinboard.model.Bulletin;
import com.denyskozii.bulletinboard.model.Role;
import com.denyskozii.bulletinboard.model.User;
import com.denyskozii.bulletinboard.repository.BulletinRepository;
import com.denyskozii.bulletinboard.service.BulletinService;
import com.denyskozii.bulletinboard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Controller
@Slf4j
@RequestMapping("/bulletin")
public class BulletinController {

    private final BulletinService bulletinService;
    private final BulletinRepository bulletinRepository;
    private final UserService userService;

//    @Autowired
//    public BulletinController(BulletinService bulletinService, UserService userService) {
//        this.bulletinService = bulletinService;
//        this.userService = userService;
//    }

    @Autowired
    public BulletinController(BulletinService bulletinService, BulletinRepository bulletinRepository, UserService userService) {
        this.bulletinService = bulletinService;
        this.bulletinRepository = bulletinRepository;
        this.userService = userService;
    }

    @GetMapping
    public String showPage(Model model,
                            @RequestParam(defaultValue = "1") int pageIndex) {
        PageRequest pagesRequest = PageRequest.of(pageIndex-1,10,Sort.Direction.ASC,"id");
        Page<Bulletin> page = bulletinRepository.findAll(pagesRequest);
        model.addAttribute("data", page);
        model.addAttribute("currentPage", pageIndex);
        return "bulletin/list";
    }


    @PostMapping("/add")
    public String createBulletin(@Valid @ModelAttribute BulletinDto bulletinDto,
                                 HttpServletRequest request,
                                 BindingResult bindingResult) {
        UserDto userDto = (UserDto) ((UsernamePasswordAuthenticationToken)request.getUserPrincipal()).getPrincipal();

        bulletinDto.setStartDate(LocalDate.now());
        bulletinDto.setAuthor(userDto);

        if (bindingResult.hasErrors()) {
            log.error("Error(s) creating bulletin" + bindingResult.getAllErrors());
            return "user/account";
        }
        log.info("Creating new bulletin");
        bulletinService.createOrUpdateBulletin(bulletinDto);
        return "redirect:/bulletin";
    }
}


