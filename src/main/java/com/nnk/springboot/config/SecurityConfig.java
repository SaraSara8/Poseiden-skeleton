package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Classe de configuration de la sécurité Spring Security.
 * <p>
 * Cette classe configure les règles de sécurité, les filtres d'authentification et d'autorisation,
 * et les encodeurs pour protéger l'application.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /**
     * Logger pour suivre les événements liés à la configuration de la sécurité.
     */
    private static final Logger logger = LogManager.getLogger(SecurityConfig.class);

    /**
     * Service utilisateur personnalisé pour gérer les authentifications.
     */
    private final CustomUserDetailsService customUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructeur pour injecter les dépendances nécessaires.
     *
     * @param customUserDetailsService Service utilisateur personnalisé.
     * @param passwordEncoder
     */
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Configure les règles de filtrage et les permissions d'accès pour l'application.
     *
     * @param http L'objet HttpSecurity permettant de configurer la sécurité.
     * @return La chaîne de filtres de sécurité configurée.
     * @throws Exception Si une erreur survient lors de la configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/login", "/css/**", "/js/**", "/error").permitAll() // Autoriser l'accès public à ces routes
                        .requestMatchers("/user/**", "/home", "/").hasAuthority("ROLE_ADMIN") // Pages accessibles uniquement aux administrateurs
                        .anyRequest().authenticated() // Toutes les autres pages nécessitent une authentification
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Page de connexion personnalisée
                        .successHandler(customAuthenticationSuccessHandler()) // Gestionnaire de succès personnalisé
                        .permitAll()
                )
                .logout(logout -> {
                    logger.info("Configuration de la déconnexion"); // Log d'information
                    logout
                            .logoutSuccessUrl("/login?logout") // Redirection après déconnexion
                            .invalidateHttpSession(true) // Invalider la session
                            .deleteCookies("JSESSIONID") // Supprimer les cookies de session
                            .permitAll();
                })
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedPage("/error") // Redirection en cas d'accès interdit
                )
                .userDetailsService(customUserDetailsService); // Utiliser le service utilisateur personnalisé
        logger.info("Configuration de la chaîne de filtres de sécurité terminée.");
        return http.build();
    }

    /**
     * Configure le fournisseur d'authentification avec le service utilisateur et un encodeur de mots de passe.
     *
     * @return Un fournisseur d'authentification configuré.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        logger.info("Configuration du fournisseur d'authentification avec un encodeur BCrypt.");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);



        return authProvider;
    }



    /**
     * Définit un gestionnaire personnalisé pour gérer les succès d'authentification.
     *
     * @return Un gestionnaire de succès d'authentification personnalisé.
     */
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        logger.info("Création du gestionnaire personnalisé de succès d'authentification.");
        return new CustomAuthenticationSuccessHandler();
    }
}
