package com.example.sweater.web.controller;


import com.example.sweater.hendler.UserHandler;
import com.example.sweater.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private UserHandler userHandler;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
    public String addUser(User user, Model model, @RequestParam("g-recaptcha-response") String captchaResponse) {
        return userHandler.registration(user, model, captchaResponse);
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        return userHandler.activateUser(model, code);
    }
}
