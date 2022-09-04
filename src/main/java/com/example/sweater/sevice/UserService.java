package com.example.sweater.sevice;

import com.example.sweater.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    User findByName(String name);

    User findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> getUsers();

    User findByActivationCode(String code);

}
