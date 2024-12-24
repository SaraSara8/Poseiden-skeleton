package com.nnk.springboot.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration de l'encodage du mot de passe
 */
@Configuration
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    /**
     * Fournit un encodeur de mots de passe utilisant BCrypt.
     *
     * @return Un PasswordEncoder utilisant BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("genéré le mot de passe");
        return new BCryptPasswordEncoder();
    }
}