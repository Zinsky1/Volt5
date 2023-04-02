package com.example.volt.service;


import com.example.volt.dao.UserDao;
import com.example.volt.model.Role;
import com.example.volt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> roleSet = Arrays.asList(new Role("ROLE_USER"));
        user.setRoles(roleSet);
        userDao.add(user);
    }

    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> roleSet = Arrays.asList(new Role("ROLE_USER"));
        user.setRoles(roleSet);
        userDao.updateUser(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails
                .User(user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
