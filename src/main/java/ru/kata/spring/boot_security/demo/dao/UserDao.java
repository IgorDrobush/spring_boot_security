package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    void saveUser(User user);

    void deleteUser(User user);

    User findUserById(long id);

    User findUserByUsername(String username);

    void updateUser(User user, User userToUpdate);

    List<User> getAllUsers();
}
