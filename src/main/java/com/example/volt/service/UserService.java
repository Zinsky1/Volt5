package com.example.volt.service;


import com.example.volt.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    List<User> listUsers();
    User getUser(Long id);
    void deleteUser(Long id);
    void updateUser(User user);
    User findUserByUsername(String username);
}
