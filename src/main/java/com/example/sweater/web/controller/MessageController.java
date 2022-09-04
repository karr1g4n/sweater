package com.example.sweater.web.controller;

import com.example.sweater.config.CustomUserDetails;
import com.example.sweater.hendler.MessageHandler;
import com.example.sweater.model.Message;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Controller
@AllArgsConstructor
public class MessageController {

    private MessageHandler messageHandler;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false) String filter, Model model) {
        if (filter != null && !filter.isEmpty()) {
            return messageHandler.filterMessageByTag(filter, model);
        }
        return messageHandler.getAllMessages(model);
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid Message message,
            Model model,
            @RequestParam("file") MultipartFile multipartFile) throws IOException {
        return messageHandler.addMessage(message, model, user, multipartFile);
    }

}
