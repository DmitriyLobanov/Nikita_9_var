package ru.tpu.sergeev.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tpu.sergeev.dto.UserDto;
import ru.tpu.sergeev.models.User;
import ru.tpu.sergeev.services.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/auth")
@AllArgsConstructor
@Controller
public class AuthController {

    public static Logger slf4jLogger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register-page";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute UserDto userDto) {
        if (!userService.registerUser(userDto))
            return "redirect:/auth/registration";
        return "redirect:/files";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "login-page";
    }
    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/";
    }

    @GetMapping("/done")
    public String startPage( HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (request.isUserInRole("ROLE_ADMIN")) {
            slf4jLogger.info("Пользователь " + user.getUsername() +" авторизовался как администратор");
            return "redirect:/admin/";
        }
        slf4jLogger.info("Пользователь " + user.getUsername() +" авторизовался");
        return "redirect:/files/";
    }

}
