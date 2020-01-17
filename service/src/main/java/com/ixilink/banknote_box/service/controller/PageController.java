package com.ixilink.banknote_box.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * description:
 * author: 张俊
 * date: 2019-11-04 16:57
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @GetMapping("/error")
    public ModelAndView index(HttpServletRequest request)  {
        return new ModelAndView("404");
    }
}
