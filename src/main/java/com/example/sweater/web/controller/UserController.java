package com.example.sweater.web.controller;

import com.example.sweater.hendler.UserHandler;
import com.example.sweater.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserHandler userHandler;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String userList(@Validated Model model) {
        return userHandler.getUserList(model);
    }


    @GetMapping("{user}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String userEditForm(@Validated @PathVariable Long id, Model model) {
        return userHandler.getUserForm(id, model);
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String updateUserData(@Validated @RequestParam Map<String, String> form,
                                 @RequestParam(required = false) User user) {
        return userHandler.updateUserRole(form, user);
    }


    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal UserDetails user) {
        return userHandler.getProfile(model, user);
    }


    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal UserDetails user, @RequestParam String password, String email) {
        return userHandler.updateUserPasswordAndEmail(user, password, email);
    }


}
