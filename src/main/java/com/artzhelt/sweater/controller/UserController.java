package com.artzhelt.sweater.controller;

import com.artzhelt.sweater.auth.SweaterUserService;
import com.artzhelt.sweater.domain.Role;
import com.artzhelt.sweater.domain.SweaterUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final SweaterUserService userService;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @GetMapping("/{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEditForm(@PathVariable SweaterUser user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveUser(@RequestParam("userId") SweaterUser user,
                           @RequestParam Map<String, String> form,
                           @RequestParam String username)
    {
        Set<Role> newRoles = getRolesFromForm(form);
        userService.save(user, newRoles, username);
        return "redirect:/user";
    }

    private static Set<Role> getRolesFromForm(Map<String, String> form) {
        return form
                .keySet().stream()
                .filter(Role::contains)
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal SweaterUser user) {
        model.addAttribute("username", user.getUsername());
        return "profile";
    }

    @PostMapping("/profile")
    public String saveProfile(@AuthenticationPrincipal SweaterUser user, @RequestParam String password) {
        userService.saveProfile(user, password);
        return "redirect:/user/profile";
    }
}
