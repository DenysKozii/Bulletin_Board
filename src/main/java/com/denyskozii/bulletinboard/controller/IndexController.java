package com.denyskozii.bulletinboard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Spring Controller (start page)
 *
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Controller
@Slf4j
public class IndexController {

    @Autowired
    public IndexController() { }

    /**
     * Greeting page by localhost:8080/
     *
     * @return String
     */
    @GetMapping
    public String showIndex() {
        log.info("Rendering index.html");
        return "index";
    }
}
