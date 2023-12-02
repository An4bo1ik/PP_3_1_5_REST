package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
public class UserController {
    private final UserServiceImpl userService;

    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserServiceImpl userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/index")
    public String indexPage() {
        return "/index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String adminPage(@ModelAttribute("user") User user, Model model, Principal principal) {
        model.addAttribute("roles", userService.getAllRoles());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("usingUser", userService.findByName(principal.getName()));
        return "/allUsers";
    }

    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        model.addAttribute("user", userService.findByName(principal.getName()));
        return "/user";
    }

    @GetMapping("/admin/edit")
    public String editUserPage(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "redirect:/admin/edit";
    }

    @PatchMapping("/admin/update/{id}")
    public String saveEdit(@PathVariable("id") Long id,
                           @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/update/{id}";
        }
        userService.updateUser(user, id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/create")
    public String createUserPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "redirect:/create";
    }

    @PostMapping("/admin/create")
    public String saveUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/create";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @PostMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
