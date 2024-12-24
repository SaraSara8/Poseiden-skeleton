package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
 * Contrôleur pour la gestion des opérations sur les CurvePoints.
 */
@Controller
public class CurveController {

    private static final Logger logger = LogManager.getLogger(CurveController.class);
    private final CurvePointService curvePointService;

    @Value("${spring.data.web.pageable.default-page-size}")
    private int defaultPageSize;

    @Value("${spring.data.web.pageable.max-page-size}")
    private int maxPageSize;


    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    /**
     * Affiche la liste des CurvePoints.
     *
     * @param page  Numéro de la page à afficher (par défaut 0).
     * @param size  Taille de la page (par défaut configurée dans les propriétés de l'application).
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour afficher les CurvePoints.
     */
    @RequestMapping("/curvePoint/list")
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "#{@environment.getProperty('spring.data.web.pageable.default-page-size')}") int size,
                       Model model) {

        logger.info("Récupération de tous les CurvePoints.");

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

        Page<CurvePoint>curvePointPage = curvePointService.findPaginated(PageRequest.of(page, size));

        model.addAttribute("curvePoints", curvePointPage.getContent()); // Contenu de la page
        model.addAttribute("currentPage", page); // Page actuelle
        model.addAttribute("totalPages", curvePointPage.getTotalPages()); // Nombre total de pages

        return "curvePoint/list";
    }

    /**
     * Affiche le formulaire pour ajouter un nouveau CurvePoint.
     *
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour ajouter un CurvePoint.
     */
    @GetMapping("/curvePoint/add")
    public String addCurveForm(Model model) {
        logger.info("Affichage du formulaire pour ajouter un nouveau CurvePoint.");
        model.addAttribute("curvePoint", new CurvePoint());
        return "curvePoint/add";
    }

    /**
     * Valide et enregistre un nouveau CurvePoint.
     *
     * @param curvePoint CurvePoint à valider et enregistrer.
     * @param result     Résultat de la validation.
     * @param model      Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste si réussite, sinon recharge le formulaire.
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Échec de la validation pour le CurvePoint : {}", result.getAllErrors());
            return "curvePoint/add";
        }
        logger.info("Enregistrement d'un nouveau CurvePoint : {}", curvePoint);
        curvePointService.insert(curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Affiche le formulaire pour mettre à jour un CurvePoint existant.
     *
     * @param id    Identifiant du CurvePoint à mettre à jour.
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour mettre à jour un CurvePoint.
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Récupération du CurvePoint avec l'ID : {}", id);
        CurvePoint curvePoint = curvePointService.findCurvePoint(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /**
     * Met à jour un CurvePoint existant.
     *
     * @param id         Identifiant du CurvePoint à mettre à jour.
     * @param curvePoint Données mises à jour du CurvePoint.
     * @param result     Résultat de la validation.
     * @param model      Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste si réussite, sinon recharge le formulaire.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Échec de la validation pour la mise à jour du CurvePoint : {}", result.getAllErrors());
            return "curvePoint/update";
        }
        logger.info("Mise à jour du CurvePoint avec l'ID : {}", id);
        curvePointService.insert(curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Supprime un CurvePoint existant.
     *
     * @param id    Identifiant du CurvePoint à supprimer.
     * @param model Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste après suppression.
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) {
        logger.info("Suppression du CurvePoint avec l'ID : {}", id);
        CurvePoint curvePoint = curvePointService.findCurvePoint(id);
        if (curvePoint != null) {
            curvePointService.delete(curvePoint);
        }
        return "redirect:/curvePoint/list";
    }
}
