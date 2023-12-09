package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByName(String name) {
        return userRepository.findUserByUsername(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (findByName(user.getUsername()).isPresent()) {
            throw new EntityExistsException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    @Override
    @Transactional
    public void updateUser(User user, Long id) {
        User userDb = findUserById(id);
        if (userDb == null) {
            throw new EntityNotFoundException("Incorrect data");
        }

        userDb.setUsername(user.getUsername());
        userDb.setLastName(user.getLastName());
        userDb.setAge(user.getAge());
        if (!userDb.getPassword().equals(user.getPassword())) {
            userDb.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDb.setRoles(user.getRoles());

        userRepository.flush();
    }
}

