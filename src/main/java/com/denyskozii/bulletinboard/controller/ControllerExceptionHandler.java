package com.denyskozii.bulletinboard.controller;

import com.denyskozii.bulletinboard.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring Controller with exception handling
 *
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    /**
     * Catching EntityNotFoundException
     * @param request
     * @param e
     * @return <code>ModelAndView</code>
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleNotFoundException(HttpServletRequest request,
                                                Exception e) {
        log.info("EntityNotFoundException occurred::" +
                "URL="+request.getRequestURL());
        ModelAndView modelAndView = new ModelAndView("error/error", HttpStatus.NOT_FOUND);
        modelAndView.addObject("info", e.getMessage());
        return modelAndView;
    }

    /**
     * Catching DataIntegrityViolationException
     * @param request
     * @param e
     * @return <code>ModelAndView</code>
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityViolationException(HttpServletRequest request,
                                                Exception e) {
        log.info("DataIntegrityViolationException occurred::" +
                "URL="+request.getRequestURL());
        ModelAndView modelAndView = new ModelAndView("error/error", HttpStatus.NOT_FOUND);
        modelAndView.addObject("info",
                "Database error occurred. Please try to change your request or contact site administrator");
        return modelAndView;
    }

}
