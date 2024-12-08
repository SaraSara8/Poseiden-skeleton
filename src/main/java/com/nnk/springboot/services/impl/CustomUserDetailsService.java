package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service personnalisé pour charger les détails d'un utilisateur en fonction de son nom d'utilisateur.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Charge un utilisateur par son nom d'utilisateur.
     * @param username Le nom d'utilisateur.
     * @return Les détails de l'utilisateur.
     * @throws UsernameNotFoundException Si l'utilisateur n'est pas trouvé.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Chargement des détails de l'utilisateur avec le nom d'utilisateur : {}", username);
        User user = userService.findByUsername(username);
        if (user == null) {
            logger.error("Utilisateur non trouvé avec le nom d'utilisateur : {}", username);
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }

        org.springframework.security.core.userdetails.User.UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(user.getPassword());
        builder.authorities(new SimpleGrantedAuthority(user.getRole())); // Charger le rôle

        return builder.build();
    }
}
