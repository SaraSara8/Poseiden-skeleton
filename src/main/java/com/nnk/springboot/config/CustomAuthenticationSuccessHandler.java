package com.nnk.springboot.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

/**
 * Gestionnaire personnalisé de succès d'authentification pour gérer la redirection après une connexion réussie.
 * <p>
 * Ce gestionnaire détermine les rôles de l'utilisateur authentifié et effectue une redirection
 * vers la page appropriée en fonction de son rôle.
 * <p>
 * Fonctionnalités principales :
 * - Enregistre les rôles de l'utilisateur authentifié dans les logs.
 * - Redirige les administrateurs vers l'URL "/home".
 * - Redirige les autres utilisateurs vers l'URL "/bidList/list".
 * <p>
 * Cette classe est annotée avec @Component, ce qui en fait un composant géré par Spring.
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Logger pour enregistrer les événements liés au succès de l'authentification.
     */
    private static final Logger logger = LogManager.getLogger(CustomAuthenticationSuccessHandler.class);

    /**
     * Stratégie de redirection pour envoyer l'utilisateur vers une URL après une authentification réussie.
     */
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * Méthode appelée lors d'une authentification réussie.
     * <p>
     * Elle détermine le rôle de l'utilisateur authentifié et effectue une redirection
     * vers l'URL appropriée en fonction de ce rôle.
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
        logger.info("Authentification réussie pour l'utilisateur : {}", authentication.getName());

        // Récupérer les rôles de l'utilisateur authentifié
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.isEmpty()) {
            logger.error("Aucun rôle trouvé pour l'utilisateur : {}", authentication.getName());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit : aucun rôle trouvé.");
            return;
        }

        // Parcourir les rôles et rediriger en conséquence
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            logger.debug("Rôle détecté : {}", role);

            switch (role) {
                case "ROLE_ADMIN":
                    logger.info("Redirection de l'utilisateur ADMIN vers /home");
                    redirectStrategy.sendRedirect(request, response, "/");
                    return;
                case "ROLE_USER":
                    logger.info("Redirection de l'utilisateur standard vers /bidList/list");
                    redirectStrategy.sendRedirect(request, response, "/bidList/list");
                    return;
                default:
                    logger.warn("Rôle non reconnu : {}", role);
            }
        }

        // Si aucun rôle valide n'a été trouvé, générer une erreur
        logger.error("Impossible de rediriger l'utilisateur : rôle inconnu.");
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit : rôle inconnu.");
    }
}
