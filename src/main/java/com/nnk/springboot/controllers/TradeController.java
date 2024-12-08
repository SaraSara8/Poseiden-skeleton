package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Contrôleur pour la gestion des opérations sur les Trades.
 */
@Controller
public class TradeController {

    private static final Logger logger = LogManager.getLogger(TradeController.class);
    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Affiche la liste des Trades.
     *
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour afficher les Trades.
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        logger.info("Récupération de tous les Trades.");
        List<Trade> trades = tradeService.findAllTrade();
        model.addAttribute("trades", trades);
        return "trade/list";
    }

    /**
     * Affiche le formulaire pour ajouter un nouveau Trade.
     *
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour ajouter un Trade.
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Model model) {
        logger.info("Affichage du formulaire pour ajouter un nouveau Trade.");
        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    /**
     * Valide et enregistre un nouveau Trade.
     *
     * @param trade  Trade à valider et enregistrer.
     * @param result Résultat de la validation.
     * @param model  Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste si réussite, sinon recharge le formulaire.
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Échec de la validation pour le Trade : {}", result.getAllErrors());
            return "trade/add";
        }
        logger.info("Enregistrement d'un nouveau Trade : {}", trade);
        tradeService.insert(trade);
        return "redirect:/trade/list";
    }

    /**
     * Affiche le formulaire pour mettre à jour un Trade existant.
     *
     * @param id    Identifiant du Trade à mettre à jour.
     * @param model Modèle pour transmettre les données à la vue.
     * @return Nom de la vue pour mettre à jour un Trade.
     */
    @GetMapping("/trade/update/{tradeId}")
    public String showUpdateForm(@PathVariable("tradeId") Integer id, Model model) {
        logger.info("Récupération du Trade avec l'ID : {}", id);
        Trade trade = tradeService.findTrade(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
     * Met à jour un Trade existant.
     *
     * @param id     Identifiant du Trade à mettre à jour.
     * @param trade  Données mises à jour du Trade.
     * @param result Résultat de la validation.
     * @param model  Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste si réussite, sinon recharge le formulaire.
     */
    @PostMapping("/trade/update/{tradeId}")
    public String updateTrade(@PathVariable("tradeId") Integer id, @Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Échec de la validation pour la mise à jour du Trade : {}", result.getAllErrors());
            return "trade/update";
        }
        logger.info("Mise à jour du Trade avec l'ID : {}", id);
        tradeService.insert(trade);
        return "redirect:/trade/list";
    }

    /**
     * Supprime un Trade existant.
     *
     * @param id    Identifiant du Trade à supprimer.
     * @param model Modèle pour transmettre les données à la vue.
     * @return Redirection vers la liste après suppression.
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.info("Suppression du Trade avec l'ID : {}", id);
        Trade trade = tradeService.findTrade(id);
        if (trade != null) {
            tradeService.delete(trade);
        }
        return "redirect:/trade/list";
    }
}
