package com.nnk.springboot.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Gestionnaire personnalisé de succès d'authentification pour gérer la redirection après une connexion réussie.
 *
 * Ce gestionnaire détermine les rôles de l'utilisateur authentifié et effectue une redirection
 * vers la page appropriée en fonction de son rôle.
 *
 * Fonctionnalités principales :
 * - Enregistre les rôles de l'utilisateur authentifié dans les logs.
 * - Redirige les administrateurs vers l'URL "/home".
 * - Redirige les autres utilisateurs vers l'URL "/bidList/list".
 *
 * Cette classe est annotée avec @Component, ce qui en fait un composant géré par Spring.
 *
 * Auteur : [Votre Nom]
 * Version : 1.0
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Logger pour enregistrer les événements liés au succès de l'authentification.
     */
    private static final Logger logger = LogManager.getLogger(CustomAuthenticationSuccessHandler.class);

    /**
     * Méthode appelée lors d'une authentification réussie.
     *
     * @param request        L'objet HttpServletRequest associé à la requête HTTP.
     * @param response       L'objet HttpServletResponse associé à la réponse HTTP.
     * @param authentication L'objet Authentication contenant les informations de l'utilisateur authentifié.
     * @throws IOException      Si une erreur d'entrée/sortie survient.
     * @throws ServletException Si une erreur liée au servlet survient.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // Récupérer les rôles de l'utilisateur authentifié
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Enregistrement des rôles dans les logs
        logger.info("Authentification réussie. Rôles de l'utilisateur : {}", roles);

        // Redirection basée sur le rôle de l'utilisateur
        if (roles.contains("ADMIN")) {
            logger.info("Redirection de l'utilisateur ADMIN vers /home");
            response.sendRedirect("/home");
        } else {
            logger.info("Redirection de l'utilisateur standard vers /bidList/list");
            response.sendRedirect("/bidList/list");
        }
    }
}
