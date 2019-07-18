package com.example.springbootthymeleaflayout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "home";
    }
    @RequestMapping("/project")
    public String project() {
        return "project";
    }
    @RequestMapping("/home")
    public String home() {
        return "home";
    }
}
