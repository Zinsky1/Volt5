package com.example.volt.controller;

import com.example.volt.model.Role;
import com.example.volt.model.User;
import com.example.volt.service.UserService;
import com.example.volt.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;


@Controller
public class MainController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public MainController(UserService userService, PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
        //home
    @GetMapping
    public String home() {
        return "index";
    }

    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        model.addAttribute("user",
                userService.findUserByUsername(principal.getName()));

        return "user";
    }


        //admin CRUD
    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "admin/admin";
    }

    @GetMapping("/admin/user/{username}")
    public String getUserPage(@PathVariable("username") String username, Model model) {
        model.addAttribute("user", userService.findUserByUsername(username));
        return "admin/get";
    }

    @GetMapping("/admin/new")
    public String newUserPage(@ModelAttribute("user") User user) {
        return "admin/new";
    }


    @PostMapping("/admin/new")
    public String adding(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String updateUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));

        return "admin/update";
    }

    @PostMapping("/admin/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }






}
