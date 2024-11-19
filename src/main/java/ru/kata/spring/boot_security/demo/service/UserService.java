package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    User findUserById(long id);

    void deleteUserById(long id);

    void updateUser(User user);

    List<User> getAllUsers();
}
