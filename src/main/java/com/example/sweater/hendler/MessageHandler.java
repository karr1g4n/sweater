package com.example.sweater.hendler;

import com.example.sweater.model.Message;
import com.example.sweater.model.User;
import com.example.sweater.sevice.FileService;
import com.example.sweater.sevice.MessageService;
import com.example.sweater.sevice.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class MessageHandler {

    private MessageService messageService;

    private UserService userService;

    private FileService fileService;

    public String getAllMessages(Model model) {
        List<Message> messages = messageService.getAll();
        model.addAttribute("messages", messages);
        return "main";
    }

    public String addMessage(Message message, Model model, UserDetails userDetails,
                             MultipartFile multipartFile) throws IOException {
        User user = userService.findByName(userDetails.getUsername());
        message.setAuthor(user);
        fileService.saveFile(multipartFile, message);
        messageService.addMessage(message);
        model.addAttribute("messages", messageService.getAll());
        return "main";

    }


    public String filterMessageByTag(String filter, Model model) {
        model.addAttribute("messages", messageService.findByTag(filter));
        return "main";
    }
}
