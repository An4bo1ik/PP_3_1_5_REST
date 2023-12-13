package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
public class PageController {
    private final UserService userService;

    @Autowired
    public PageController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        model.addAttribute("user", userService.findByName(principal.getName()));
        return "/user";
    }

    @GetMapping("/admin")
    public String adminPage(@ModelAttribute("user") User user, Model model, Principal principal) {
        model.addAttribute("roles", userService.getAllRoles());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("usingUser", userService.findByName(principal.getName()));
        return "/allUsers";
    }
}
