package com.denyskozii.bulletinboard.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Controller
@Slf4j
public class IndexController {


    @Autowired
    public IndexController() {
    }

    @GetMapping
    public String showIndex() {
        log.info("Rendering index.html");
        return "index";
    }

}
