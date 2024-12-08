package com.nnk.springboot.config;

import com.nnk.springboot.services.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Classe de configuration de la sécurité Spring Security.
 *
 * Cette classe configure les filtres de sécurité, les gestionnaires d'authentification et les règles
 * d'accès pour l'application web.
 *
 * Fonctionnalités principales :
 * - Définir les permissions d'accès en fonction des rôles d'utilisateur.
 * - Personnaliser la page de connexion et le gestionnaire de succès d'authentification.
 * - Configurer le fournisseur d'authentification basé sur un service utilisateur personnalisé.
 * - Configurer un encodeur de mots de passe pour sécuriser les mots de passe.
 *
 * Auteur : [Votre Nom]
 * Version : 1.0
 */
@Configuration
public class SecurityConfig {

    /**
     * Logger pour enregistrer les événements liés à la configuration de la sécurité.
     */
    private static final Logger logger = LogManager.getLogger(SecurityConfig.class);

    /**
     * Service utilisateur personnalisé pour charger les utilisateurs.
     */
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Gestionnaire personnalisé pour gérer les succès d'authentification.
     */
    private final CustomAuthenticationSuccessHandler successHandler;

    /**
     * Constructeur pour injecter les dépendances.
     *
     * @param customUserDetailsService Service utilisateur personnalisé.
     * @param successHandler           Gestionnaire personnalisé de succès d'authentification.
     */
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, CustomAuthenticationSuccessHandler successHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.successHandler = successHandler;
    }

    /**
     * Définit les règles de filtrage et les permissions d'accès pour l'application.
     *
     * @param http L'objet HttpSecurity pour configurer les règles de sécurité.
     * @return La chaîne de filtres de sécurité configurée.
     * @throws Exception Si une erreur survient lors de la configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/login").permitAll()  // Autoriser l'accès public à cette route
                        .requestMatchers("/user/**").hasAuthority("ADMIN") // Pages accessibles uniquement aux administrateurs
                        .anyRequest().authenticated()  // Toutes les autres pages nécessitent une authentification
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Page de connexion personnalisée
                        .successHandler(successHandler)  // Gestionnaire personnalisé de succès
                        .permitAll()
                )
                .logout(logout -> {
                    logger.info("Configuration de la déconnexion"); // Log de la déconnexion
                    logout
                            .logoutSuccessUrl("/login?logout") // Rediriger après la déconnexion
                            .invalidateHttpSession(true) // Invalider la session
                            .deleteCookies("JSESSIONID") // Supprimer le cookie de session
                            .permitAll();
                })
                .userDetailsService(customUserDetailsService); // Utiliser le service utilisateur personnalisé

        return http.build();
    }

    /**
     * Configure le fournisseur d'authentification avec un service utilisateur et un encodeur de mots de passe.
     *
     * @return Le fournisseur d'authentification configuré.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Crée un encodeur de mots de passe pour sécuriser les mots de passe dans l'application.
     *
     * @return Un encodeur de mots de passe basé sur BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
