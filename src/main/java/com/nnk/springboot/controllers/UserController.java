package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Contrôleur pour la gestion des opérations sur les Utilisateurs (Users).
 */
@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);
    private final UserService userService;

    @Value("${spring.data.web.pageable.default-page-size}")
    private int defaultPageSize;

    @Value("${spring.data.web.pageable.max-page-size}")
    private int maxPageSize;


    public UserController(UserService userService) {
        this.userService = userService;
    }



    /**
     * Affiche la liste des Utilisateurs.
     *
     * @param page  Numéro de la page à afficher (par défaut 0).
     * @param size  Taille de la page (par défaut configurée dans les propriétés de l'application).
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour afficher les Utilisateurs.
     */
    @RequestMapping("/user/list")
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "#{@environment.getProperty('spring.data.web.pageable.default-page-size')}") int size,
                       Model model) {

        logger.info("Récupération de tous les Utilisateurs.");

        if (size > maxPageSize) {
            size = maxPageSize;
        }

        Page<User> userPage = userService.findPaginated(PageRequest.of(page, size));

        model.addAttribute("users", userPage.getContent()); // Contenu de la page
        model.addAttribute("currentPage", page); // Page actuelle
        model.addAttribute("totalPages", userPage.getTotalPages()); // Nombre total de pages

        return "user/list";
    }

    /**
     * Affiche le formulaire pour ajouter un nouvel Utilisateur.
     *
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour ajouter un Utilisateur.
     */
    @GetMapping("/user/add")
    public String addUserForm(Model model) {
        logger.info("Affichage du formulaire pour ajouter un nouvel Utilisateur.");
        model.addAttribute("user", new User());
        return "user/add";
    }

    /**
     * Valide et enregistre un nouvel Utilisateur.
     *
     * @param user   Utilisateur à valider et enregistrer.
     * @param result Résultat de la validation.
     * @param model  Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste si réussite, sinon recharge le formulaire.
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Échec de la validation pour l'Utilisateur : {}", result.getAllErrors());
            return "user/add";
        }
        logger.info("Enregistrement d'un nouvel Utilisateur : {}", user);
        userService.insert(user);
        return "redirect:/user/list";
    }

    /**
     * Affiche le formulaire pour mettre à jour un Utilisateur existant.
     *
     * @param id    Identifiant de l'Utilisateur à mettre à jour.
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour mettre à jour un Utilisateur.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Récupération de l'Utilisateur avec l'ID : {}", id);
        User user = userService.findUser(id);
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Met à jour un Utilisateur existant.
     *
     * @param id     Identifiant de l'Utilisateur à mettre à jour.
     * @param user   Données mises à jour de l'Utilisateur.
     * @param result Résultat de la validation.
     * @param model  Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste si réussite, sinon recharge le formulaire.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Échec de la validation pour la mise à jour de l'Utilisateur : {}", result.getAllErrors());
            return "user/update";
        }
        logger.info("Mise à jour de l'Utilisateur avec l'ID : {}", id);
        userService.update(user);
        return "redirect:/user/list";
    }

    /**
     * Supprime un Utilisateur existant.
     *
     * @param id    Identifiant de l'Utilisateur à supprimer.
     * @param model Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste après suppression.
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        logger.info("Suppression de l'Utilisateur avec l'ID : {}", id);
        User user = userService.findUser(id);
        if (user != null) {
            userService.delete(user);
        }
        return "redirect:/user/list";
    }
}
