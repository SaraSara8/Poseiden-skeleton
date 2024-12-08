package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class CurvePointController {

    private static final Logger logger = LogManager.getLogger(CurvePointController.class);
    private final CurvePointService curvePointService;

    public CurvePointController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    /**
     * Affiche la liste des CurvePoints.
     *
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour afficher les CurvePoints.
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        logger.info("Récupération de tous les CurvePoints.");
        List<CurvePoint> curvePoints = curvePointService.findAllCurvePoints();
        model.addAttribute("curvePoints", curvePoints);
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
