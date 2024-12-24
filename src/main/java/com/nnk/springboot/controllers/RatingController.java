package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Contrôleur pour la gestion des opérations sur les Ratings.
 */
@Controller
public class RatingController {

    private static final Logger logger = LogManager.getLogger(RatingController.class);
    private final RatingService ratingService;

    @Value("${spring.data.web.pageable.default-page-size}")
    private int defaultPageSize;

    @Value("${spring.data.web.pageable.max-page-size}")
    private int maxPageSize;


    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * Affiche la liste des Ratings.
     *
     * @param page  Numéro de la page à afficher (par défaut 0).
     * @param size  Taille de la page (par défaut configurée dans les propriétés de l'application).
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour afficher les Ratings.
     */
    @RequestMapping("/rating/list")
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "#{@environment.getProperty('spring.data.web.pageable.default-page-size')}") int size,
                       Model model) {

        logger.info("Récupération de tous les Ratings.");

        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("loggedInUser", authentication.getName()); // Nom de l'utilisateur
        } else {
            model.addAttribute("loggedInUser", "Anonymous");
        }


        if (size > maxPageSize) {
            size = maxPageSize;
        }


        Page<Rating> ratingPage = ratingService.findPaginated(PageRequest.of(page, size));

        model.addAttribute("ratings", ratingPage.getContent()); // Contenu de la page
        model.addAttribute("currentPage", page); // Page actuelle
        model.addAttribute("totalPages", ratingPage.getTotalPages()); // Nombre total de pages

        return "rating/list";
    }

    /**
     * Affiche le formulaire pour ajouter un nouveau Rating.
     *
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour ajouter un Rating.
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        logger.info("Affichage du formulaire pour ajouter un nouveau Rating.");
        model.addAttribute("rating", new Rating());
        return "rating/add";
    }

    /**
     * Valide et enregistre un nouveau Rating.
     *
     * @param rating Rating à valider et enregistrer.
     * @param result Résultat de la validation.
     * @param model  Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste si réussite, sinon recharge le formulaire.
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Échec de la validation pour le Rating : {}", result.getAllErrors());
            return "rating/add";
        }
        logger.info("Enregistrement d'un nouveau Rating : {}", rating);
        ratingService.insert(rating);
        return "redirect:/rating/list";
    }

    /**
     * Affiche le formulaire pour mettre à jour un Rating existant.
     *
     * @param id    Identifiant du Rating à mettre à jour.
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour mettre à jour un Rating.
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Récupération du Rating avec l'ID : {}", id);
        Rating rating = ratingService.findRating(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * Met à jour un Rating existant.
     *
     * @param id      Identifiant du Rating à mettre à jour.
     * @param rating  Données mises à jour du Rating.
     * @param result  Résultat de la validation.
     * @param model   Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste si réussite, sinon recharge le formulaire.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Échec de la validation pour la mise à jour du Rating : {}", result.getAllErrors());
            return "rating/update";
        }
        logger.info("Mise à jour du Rating avec l'ID : {}", id);
        ratingService.insert(rating);
        return "redirect:/rating/list";
    }

    /**
     * Supprime un Rating existant.
     *
     * @param id    Identifiant du Rating à supprimer.
     * @param model Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste après suppression.
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.info("Suppression du Rating avec l'ID : {}", id);
        Rating rating = ratingService.findRating(id);
        if (rating != null) {
            ratingService.delete(rating);
        }
        return "redirect:/rating/list";
    }
}
