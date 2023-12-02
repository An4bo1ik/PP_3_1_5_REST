package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(User user);

    void deleteUser(Long id);

    void updateUser(User user, Long id);

    List<User> getAllUsers();

    User findUserById(Long id);

    Optional<User> findByName(String name);

    List<Role> getAllRoles();
}
