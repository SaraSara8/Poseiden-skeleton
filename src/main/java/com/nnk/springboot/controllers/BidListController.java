package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import jakarta.servlet.http.HttpServletRequest;
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
 * Contrôleur pour la gestion des opérations sur les BidLists.
 */
@Controller
public class BidListController {

    private static final Logger logger = LogManager.getLogger(BidListController.class);
    private final BidListService bidListService;


    @Value("${spring.data.web.pageable.default-page-size}")
    private int defaultPageSize;

    @Value("${spring.data.web.pageable.max-page-size}")
    private int maxPageSize;


    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    /**
     * Affiche la liste des BidLists avec prise en charge de la pagination.
     *
     * @param page  Numéro de la page à afficher (par défaut 0).
     * @param size  Taille de la page (par défaut configurée dans les propriétés de l'application).
     * @param model Modèle pour transmettre les données à la vue.
     * @return Le nom de la vue pour afficher la liste des BidLists.
     */
    @RequestMapping("/bidList/list")
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "#{@environment.getProperty('spring.data.web.pageable.default-page-size')}") int size,
                       Model model) {

        logger.info("Récupération de toutes les BidLists.");

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


        Page<BidList> bidListPage = bidListService.findPaginated(PageRequest.of(page, size));

        model.addAttribute("bidLists", bidListPage.getContent()); // Contenu de la page
        model.addAttribute("currentPage", page); // Page actuelle
        model.addAttribute("totalPages", bidListPage.getTotalPages()); // Nombre total de pages


        return "bidList/list";
    }

    /**
     * Affiche le formulaire pour ajouter une nouvelle BidList.
     *
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour ajouter une BidList.
     */
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        logger.info("Affichage du formulaire pour ajouter une nouvelle BidList.");
        model.addAttribute("bidList", new BidList());
        return "bidList/add";
    }

    /**
     * Valide et enregistre une nouvelle BidList.
     *
     * @param bidList BidList à valider et enregistrer.
     * @param result  Résultat de la validation.
     * @param model   Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste si réussite, sinon recharge le formulaire.
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Échec de la validation pour la BidList : {}", result.getAllErrors());
            return "bidList/add";
        }
        logger.info("Enregistrement d'une nouvelle BidList : {}", bidList);
        bidListService.insert(bidList);
        return "redirect:/bidList/list";
    }

    /**
     * Affiche le formulaire pour mettre à jour une BidList existante.
     *
     * @param id    Identifiant de la BidList à mettre à jour.
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour mettre à jour une BidList.
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Récupération de la BidList avec l'ID : {}", id);
        BidList bidList = bidListService.findBidList(id);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    /**
     * Met à jour une BidList existante.
     *
     * @param id      Identifiant de la BidList à mettre à jour.
     * @param bidList Données mises à jour de la BidList.
     * @param result  Résultat de la validation.
     * @param model   Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste si réussite, sinon recharge le formulaire.
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Échec de la validation pour la mise à jour de la BidList : {}", result.getAllErrors());
            return "bidList/update";
        }
        logger.info("Mise à jour de la BidList avec l'ID : {}", id);
        // Assurez que l'ID est défini sur l'objet bidList
        bidList.setBidListId(id);
        bidListService.insert(bidList);
        return "redirect:/bidList/list";
    }

    /**
     * Supprime une BidList existante.
     *
     * @param id    Identifiant de la BidList à supprimer.
     * @param model Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste après suppression.
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.info("Suppression de la BidList avec l'ID : {}", id);
        BidList bidList = bidListService.findBidList(id);
        if (bidList != null) {
            bidListService.delete(bidList);
        }
        return "redirect:/bidList/list";
    }
}
