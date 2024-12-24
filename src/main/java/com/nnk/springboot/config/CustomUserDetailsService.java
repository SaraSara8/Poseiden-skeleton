package com.nnk.springboot.config;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service personnalisé pour charger les détails d'un utilisateur en fonction de son nom d'utilisateur.
 * <p>
 * Ce service utilise un {@link UserService} pour rechercher les informations d'un utilisateur
 * dans la base de données et les convertir en un objet {@link UserDetails} compatible avec Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Logger pour enregistrer les événements liés au chargement des utilisateurs.
     */
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    /**
     * Service permettant d'interagir avec les utilisateurs de l'application.
     */
    private final UserService userService;

    /**
     * Constructeur pour injecter le service utilisateur.
     *
     * @param userService Le service utilisateur.
     */
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Charge un utilisateur par son nom d'utilisateur.
     * <p>
     * Cette méthode recherche l'utilisateur dans la base de données via {@link UserService}.
     * Si l'utilisateur est trouvé, ses informations sont converties en un objet
     * {@link UserDetails} comprenant ses rôles pour Spring Security.
     *
     * @param username Le nom d'utilisateur.
     * @return Les détails de l'utilisateur.
     * @throws UsernameNotFoundException Si l'utilisateur n'est pas trouvé.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Chargement des détails de l'utilisateur avec le nom d'utilisateur : {}", username);

        // Recherche de l'utilisateur dans la base de données
        User user = userService.findByUsername(username);
        if (user == null) {
            logger.error("Utilisateur non trouvé avec le nom d'utilisateur : {}", username);
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }

        logger.info("Utilisateur trouvé : {} avec le rôle : {}", user.getUsername(), user.getRole());

        // Retourne un objet UserDetails pour Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true, true, true, true,
                getAuthorities(user.getRole())
        );
    }

    /**
     * Convertit le rôle de l'utilisateur en une liste d'autorisations reconnues par Spring Security.
     *
     * @param roleUser Le rôle de l'utilisateur.
     * @return Une liste d'autorisations.
     */
    private List<GrantedAuthority> getAuthorities(String roleUser) {
        logger.debug("Conversion du rôle '{}' en autorité Spring Security.", roleUser);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleUser));

        logger.info("Autorités assignées : {}", authorities);
        return authorities;
    }
}
