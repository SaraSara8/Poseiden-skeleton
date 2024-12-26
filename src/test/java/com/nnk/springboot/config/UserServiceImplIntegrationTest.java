package com.nnk.springboot.config;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
@SpringBootTest
class UserServiceImplIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void testCreateUser() {
        // Arrange
        User newUser = new User();
        newUser.setUsername("testuser");
        newUser.setPassword("password123");
        newUser.setFullname("Test User");
        newUser.setRole("USER");

        // Act
        User savedUser = userService.insert(newUser);

        // Assert
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(passwordEncoder.matches("password123", savedUser.getPassword())).isTrue();
    }

    @Test
    void testFindAllUsers() {
        // Arrange
        User user1 = new User();
        user1.setUsername("user11");
        user1.setPassword(passwordEncoder.encode("pass1"));
        user1.setFullname("User One");
        user1.setRole("ADMIN");

        User user2 = new User();
        user2.setUsername("user21");
        user2.setPassword(passwordEncoder.encode("pass2"));
        user2.setFullname("User Two");
        user2.setRole("USER");

        userRepository.save(user1);
        userRepository.save(user2);

        // Act
        Iterable<User> users = userService.findAllUsers();

        // Assert
        assertThat(users).isNotEmpty(); // Vérifie que la liste n'est pas vide
        assertThat(users).anyMatch(user -> "user11".equals(user.getUsername())); // Vérifie que user1 est présent
        assertThat(users).anyMatch(user -> "user21".equals(user.getUsername())); // Vérifie que user2 est présent
    }
}
