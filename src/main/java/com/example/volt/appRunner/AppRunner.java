package com.example.volt.appRunner;

import com.example.volt.model.Role;
import com.example.volt.model.User;

import com.example.volt.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
@Component
public class AppRunner implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    public AppRunner(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@mail.com");
        List<Role> roleSet = Arrays.asList(
                new Role("ROLE_USER"),
                new Role("ROLE_ADMIN"));
        admin.setRoles(roleSet);
        entityManager.persist(admin);

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setEmail("user@gmail.com");
        List<Role> roleSet2 = Arrays.asList(new Role("ROLE_USER"));
        user.setRoles(roleSet2);
        entityManager.persist(user);
    }
}
