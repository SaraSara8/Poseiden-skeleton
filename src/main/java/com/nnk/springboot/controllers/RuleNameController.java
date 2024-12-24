package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
 * Contrôleur pour la gestion des opérations sur les règles de nom (RuleName).
 */
@Controller
public class RuleNameController {

    private static final Logger logger = LogManager.getLogger(RuleNameController.class);
    private final RuleNameService ruleNameService;

    @Value("${spring.data.web.pageable.default-page-size}")
    private int defaultPageSize;

    @Value("${spring.data.web.pageable.max-page-size}")
    private int maxPageSize;


    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    /**
     * Affiche la liste des règles de nom.
     *
     * @param page  Numéro de la page à afficher (par défaut 0).
     * @param size  Taille de la page (par défaut configurée dans les propriétés de l'application).
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour afficher la liste des règles de nom.
     */
    @RequestMapping("/ruleName/list")
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "#{@environment.getProperty('spring.data.web.pageable.default-page-size')}") int size,
                       Model model) {

        logger.info("Récupération de toutes les règles de nom.");

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

        Page<RuleName> ruleNamePage = ruleNameService.findPaginated(PageRequest.of(page, size));

        model.addAttribute("ruleNames", ruleNamePage.getContent()); // Contenu de la page
        model.addAttribute("currentPage", page); // Page actuelle
        model.addAttribute("totalPages", ruleNamePage.getTotalPages()); // Nombre total de pages

        return "ruleName/list";
    }

    /**
     * Affiche le formulaire pour ajouter une nouvelle règle de nom.
     *
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour ajouter une règle de nom.
     */
    @GetMapping("/ruleName/add")
    public String addRuleNameForm(Model model) {
        logger.info("Affichage du formulaire pour ajouter une nouvelle règle de nom.");
        model.addAttribute("ruleName", new RuleName());
        return "ruleName/add";
    }

    /**
     * Valide et enregistre une nouvelle règle de nom.
     *
     * @param ruleName Règle de nom à valider et enregistrer.
     * @param result   Résultat de la validation.
     * @param model    Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste des règles de nom si la validation réussie, sinon recharge le formulaire.
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Échec de la validation pour la règle de nom : {}", result.getAllErrors());
            return "ruleName/add";
        }
        logger.info("Enregistrement d'une nouvelle règle de nom : {}", ruleName);
        ruleNameService.insert(ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Affiche le formulaire pour mettre à jour une règle de nom existante.
     *
     * @param id    Identifiant de la règle de nom à mettre à jour.
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour mettre à jour une règle de nom.
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Récupération de la règle de nom avec l'ID : {}", id);
        RuleName ruleName = ruleNameService.findRuleName(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    /**
     * Met à jour une règle de nom existante.
     *
     * @param id       Identifiant de la règle de nom à mettre à jour.
     * @param ruleName Données mises à jour de la règle de nom.
     * @param result   Résultat de la validation.
     * @param model    Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste des règles de nom si la mise à jour réussie, sinon recharge le formulaire.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Échec de la validation pour la mise à jour de la règle de nom : {}", result.getAllErrors());
            return "ruleName/update";
        }
        logger.info("Mise à jour de la règle de nom avec l'ID : {}", id);
        ruleNameService.insert(ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Supprime une règle de nom existante.
     *
     * @param id    Identifiant de la règle de nom à supprimer.
     * @param model Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste des règles de nom après suppression.
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        logger.info("Suppression de la règle de nom avec l'ID : {}", id);
        RuleName ruleName = ruleNameService.findRuleName(id);
        if (ruleName != null) {
            ruleNameService.delete(ruleName);
        }
        return "redirect:/ruleName/list";
    }
}
