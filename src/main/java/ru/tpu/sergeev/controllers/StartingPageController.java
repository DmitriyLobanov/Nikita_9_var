package ru.tpu.sergeev.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class StartingPageController {

    @GetMapping
    public String startPage() {
        return "start-page";
    }
    @GetMapping("/login")
    public String start() {
        return "redirect:/auth/login";
    }
    @GetMapping("/register")
    public String register() {
        return "redirect:/auth/registration";
    }
}
