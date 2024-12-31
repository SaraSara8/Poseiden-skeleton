package com.nnk.springboot.config;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test d'intégration pour CustomUserDetailsService.
 *
 * Ce test lance un vrai contexte Spring Boot, insère des utilisateurs
 * puis vérifie le comportement de loadUserByUsername().
 */
@SpringBootTest
@Transactional
class CustomUserDetailsServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private User testUser;

    @BeforeEach
    void setUp() {
        // On crée un utilisateur "complet" qui satisfait toutes les validations
        testUser = new User();
        testUser.setUsername("john.doe");
        testUser.setPassword("encodedPassword123");
        testUser.setRole("ADMIN");
        testUser.setFullname("John Doe"); // Champ obligatoire @NotBlank

        // On l'insère en BDD via userService
        testUser = userService.insert(testUser);
    }

    @Test
    @DisplayName("loadUserByUsername() - utilisateur existant -> doit retourner UserDetails")
    void testLoadUserByUsername_ExistingUser() {
        // On appelle le CustomUserDetailsService pour "john.doe"
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("john.doe");

        assertNotNull(userDetails);
        assertEquals("john.doe", userDetails.getUsername());
        // Vérifier la présence de ROLE_ADMIN
        assertTrue(
                userDetails.getAuthorities().stream()
                        .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()))
        );
    }

    @Test
    @DisplayName("loadUserByUsername() - utilisateur inexistant -> doit lever UsernameNotFoundException")
    void testLoadUserByUsername_NonExistingUser() {
        assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("inexistantUser")
        );
    }
}