package com.example.sweater.hendler;

import com.example.sweater.model.User;
import com.example.sweater.model.role.Role;
import com.example.sweater.notification.EmailService;
import com.example.sweater.response.CaptchaResponse;
import com.example.sweater.sevice.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserHandler {

    private final static String CAPTChHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String secret;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final EmailService mailSender;

    private final RestTemplate restTemplate;

    public UserHandler(UserService userService, PasswordEncoder passwordEncoder, EmailService mailSender,
                       RestTemplate restTemplate) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.restTemplate = restTemplate;
    }


    public String registration(User user, Model model, String captchaResponse) {
        String url = String.format(CAPTChHA_URL, secret, captchaResponse);
        CaptchaResponse response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);
        assert response != null;
        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "заповніть капчу");
        } else {
            if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
                model.addAttribute("passwordError", "Паролі різні");
                return "registration";
            }
            User userFromDb = userService.findByName(user.getUsername());
            if (userFromDb != null) {
                model.addAttribute("usernameError", "Користувач вже зареєстрований");
                model.addAttribute("user", user);
                return "registration";

            }
            userFromDb = userService.findByEmail(user.getEmail());
            if (userFromDb != null) {
                model.addAttribute("emailError", "Пашта вже зареєстрована");
                model.addAttribute("user", user);
                return "registration";
            }
            user.setActive(true);
            user.addRole(Role.USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationCode(UUID.randomUUID().toString());
            userService.save(user);

            sendMessage(user);
            return "redirect:/login";
        }
        return "registration";
    }

    public String getUserList(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "userList";
    }

    public String getUserForm(Long id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("roles", Role.values());
            return "userEdit";
        }
        return "redirect:/errorPage";
    }


    public String updateUserRole(Map<String, String> form, User user) {
        if (user != null) {
            user.setUsername(form.get("username"));
            Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
            if (form.size() != 0) {
                for (String key : form.keySet()) {
                    if (roles.contains(key)) {
                        user.getRoles().add(Role.valueOf(key));
                    }

                }
            }
            userService.save(user);
            return "redirect:/user";
        }
        return "redirect:/errorPage";
    }


    public String activateUser(Model model, String code) {
        User user = userService.findByActivationCode(code);
        if (user != null) {
            user.setActivationCode(null);
            userService.save(user);
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activate code not found");
        }
        return "login";
    }


    public String getProfile(Model model, UserDetails user) {
        User userFromDB = userService.findByName(user.getUsername());
        if (userFromDB != null) {
            model.addAttribute("username", userFromDB.getUsername());
            model.addAttribute("email", userFromDB.getEmail());
            return "profile";
        }
        return "redirect:/errorPage";
    }


    public String updateUserPasswordAndEmail(UserDetails user, String password, String email) {
        User userFromDB = userService.findByName(user.getUsername());
        String userEmail = userFromDB.getEmail();

        boolean isEmailChanged =
                (email != null && !email.equals(userEmail)) || (userEmail != null && !userEmail.equals(email));
        if (isEmailChanged) {
            userFromDB.setEmail(email);

        }

        if (!password.isEmpty()) {
            userFromDB.setPassword(password);
        }
        userFromDB.setActivationCode(UUID.randomUUID().toString());
        userService.save(userFromDB);
        if (isEmailChanged) {
            sendMessage(userFromDB);
        }
        return "redirect:/";
    }


    private void sendMessage(User user) {
        String message = String.format(
                "Hello, %s! \n" + "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                user.getUsername(), user.getActivationCode());
        mailSender.sendEmail(user.getEmail(), "Activation code", message);
    }

}
