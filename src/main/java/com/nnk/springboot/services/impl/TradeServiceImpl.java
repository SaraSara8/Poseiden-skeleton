package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Implémentation du service Trade.
 * Ce service permet de gérer les opérations sur les transactions (trades).
 */
@Service
public class TradeServiceImpl implements TradeService {

    private static final Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

    private final TradeRepository traderepository;

    public TradeServiceImpl(TradeRepository traderepository) {
        this.traderepository = traderepository;
    }

    /**
     * Récupère toutes les transactions.
     * @return la liste des transactions.
     */
    public List<Trade> findAllTrade() {
        logger.info("Récupération de toutes les transactions");
        return traderepository.findAll();
    }



    /**
     * Récupère une page paginée de soumissions.
     *
     * @param pageable l'objet définissant la pagination (page actuelle, taille de la page, etc.).
     * @return une page contenant les instances de {@link Trade}.
     */
    @Override
    public Page<Trade> findPaginated(Pageable pageable) {
        logger.info("Récupération de toutes les transactions par page");
        return traderepository.findAll(pageable);
    }



    /**
     * Insère une nouvelle transaction.
     * @param trade La transaction à insérer.
     * @return La transaction insérée.
     */
    public Trade insert(Trade trade) {
        logger.info("Insertion de la transaction : {}", trade);
        return traderepository.save(trade);
    }

    /**
     * Supprime une transaction.
     * @param trade La transaction à supprimer.
     */
    @Transactional
    public void delete(Trade trade){
        logger.info("Suppression de la transaction avec l'ID : {}", trade.getTradeId());
        traderepository.deleteById(trade.getTradeId());
    }

    /**
     * Trouve une transaction par son ID.
     * @param id L'ID de la transaction.
     * @return La transaction trouvée ou null si non trouvée.
     */
    public Trade findTrade(Integer id){
        logger.info("Recherche de la transaction avec l'ID : {}", id);
        return traderepository.findById(id).orElse(null);
    }

    /**
     * Vérifie si une transaction existe par son ID.
     * @param id L'ID de la transaction.
     * @return true si la transaction existe, false sinon.
     */
    @Override
    public boolean existsById(int id) {
        logger.info("Vérification de l'existence de la transaction avec l'ID : {}", id);
        return traderepository.existsById(id);
    }
}
