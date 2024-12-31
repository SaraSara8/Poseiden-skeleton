package com.nnk.springboot.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test d'intégration pour CustomAuthenticationSuccessHandler.
 * <p>
 * Nous testons différentes tentatives de connexion pour vérifier
 * que la redirection est bien gérée selon le rôle de l'utilisateur.
 */
@SpringBootTest
@AutoConfigureMockMvc
class CustomAuthenticationSuccessHandlerTests {

    @Autowired
    private MockMvc mockMvc; // Injection par champ (pas de constructeur)

    /**
     * Test : Un utilisateur avec ROLE_ADMIN doit être redirigé vers "/".
     */
    @Test
    @DisplayName("Connexion en tant qu'ADMIN -> redirection vers /")
    void testAdminRedirect() throws Exception {
        mockMvc.perform(formLogin().user("admin").password("adminpw"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    /**
     * Test : Un utilisateur avec ROLE_USER doit être redirigé vers "/bidList/list".
     */
    @Test
    @DisplayName("Connexion en tant que USER -> redirection vers /bidList/list")
    void testUserRedirect() throws Exception {
        mockMvc.perform(formLogin().user("user").password("userpw"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    /**
     * Test : Mauvais mot de passe -> échec, redirection vers /login?error.
     */
    @Test
    @DisplayName("Connexion avec mauvais mot de passe -> échec => /login?error")
    void testLoginFailure() throws Exception {
        mockMvc.perform(formLogin().user("user").password("wrongpw"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));  // <-- Vérification stricte
    }



    /**
     * Configuration de test interne qui :
     * 1) active la sécurité ;
     * 2) déclare notre CustomAuthenticationSuccessHandler ;
     * 3) définit des utilisateurs en mémoire.
     */
    @Configuration
    @EnableWebSecurity
    static class TestSecurityConfig {

        /**
         * Notre handler personnalisé pour le succès de l'authentification.
         */
        @Bean
        public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
            return new CustomAuthenticationSuccessHandler();
        }

        /**
         * Définition d'une SecurityFilterChain minimaliste :
         * - Toute URL nécessite une authentification
         * - Login via formulaire, géré par notre customAuthenticationSuccessHandler()
         */
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http,
                                               CustomAuthenticationSuccessHandler successHandler) throws Exception {
            http
                    .authorizeHttpRequests(auth -> auth
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form
                            // On ne précise pas de loginPage custom,
                            // formLogin gère /login automatiquement
                            .successHandler(successHandler)
                            .permitAll()
                    )
                    .logout(logout -> logout.permitAll());

            return http.build();
        }

        /**
         * On crée un UserDetailsService en mémoire avec deux utilisateurs :
         *  - admin:ROLE_ADMIN
         *  - user:ROLE_USER
         */
        @Bean
        public UserDetailsService inMemoryUserDetails() {
            return new InMemoryUserDetailsManager(
                    User.withUsername("admin")
                            .password("{noop}adminpw") // {noop} => pas d'encodage
                            .roles("ADMIN")
                            .build(),

                    User.withUsername("user")
                            .password("{noop}userpw")
                            .roles("USER")
                            .build()
            );
        }
    }
}