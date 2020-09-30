package com.denyskozii.bulletinboard.controller;

import com.denyskozii.bulletinboard.dto.BulletinDto;
import com.denyskozii.bulletinboard.dto.UserDto;
import com.denyskozii.bulletinboard.model.Bulletin;
import com.denyskozii.bulletinboard.service.BulletinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

/**
 * Spring Controller that handles bulletins (Creating, Show with Pagination)
 *
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Controller
@Slf4j
@RequestMapping("/bulletin")
public class BulletinController {

    private final BulletinService bulletinService;

    @Value("${bulletin-page-size}")
    private int pageSize;

    @Autowired
    public BulletinController(BulletinService bulletinService)  {
        this.bulletinService = bulletinService;
    }

    @GetMapping
    public String showPage(Model model, @RequestParam(defaultValue = "1") int pageIndex) {
        PageRequest pagesRequest = PageRequest.of(pageIndex-1,pageSize,Sort.Direction.DESC,"id");
        Page<Bulletin> page = bulletinService.getPage(pagesRequest);
        model.addAttribute("data", page);
        model.addAttribute("currentPage", pageIndex);
        return "bulletin/list";
    }

    @PostMapping("/add")
    public String createBulletin(@Valid @ModelAttribute BulletinDto bulletinDto,
                                 HttpServletRequest request,
                                 BindingResult bindingResult,
                                 @RequestParam("fileImage") MultipartFile multipartFile,
                                 Model model) throws IOException {
        log.info("createBulletin controller");
        if(bulletinDto.getTitle().isEmpty()){
            Object userDto = ((UsernamePasswordAuthenticationToken)request.getUserPrincipal()).getPrincipal();
            model.addAttribute("user", userDto);
            model.addAttribute("bulletin", bulletinDto);
            model.addAttribute("message", "Title can't be empty");
            return "user/account";
        }

        UserDto userDto = (UserDto) ((UsernamePasswordAuthenticationToken)request.getUserPrincipal()).getPrincipal();
        bulletinDto.setStartDate(LocalDate.now());
        bulletinDto.setAuthor(userDto);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (!fileName.isEmpty()){
            bulletinDto.setImage(String.format("/uploads/%s", fileName));
            String uploadDir = request.getServletContext().getRealPath("/uploads");
            Files.createDirectories(Paths.get(uploadDir));
            multipartFile.transferTo(new File(String.format("%s/%s",uploadDir, fileName)));
        }

        if (bindingResult.hasErrors()) {
            log.error("Error(s) creating bulletin" + bindingResult.getAllErrors());
            return "user/account";
        }
        log.trace("Creating new bulletin");
        bulletinService.createOrUpdateBulletin(bulletinDto);
        return "redirect:/bulletin";
    }
}


