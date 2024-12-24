package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Interface pour la gestion des opérations métier liées aux entités {@link Trade}.
 * <p>
 * Cette interface définit les méthodes nécessaires pour gérer les transactions (Trade),
 * incluant la récupération, l'insertion, la suppression et la pagination.
 * <p>
 * Les implémentations de cette interface doivent encapsuler la logique métier
 * spécifique pour interagir avec les entités {@link Trade}.
 */
public interface TradeService {

    /**
     * Récupère la liste de toutes les transactions disponibles.
     *
     * @return Une liste contenant toutes les entités {@link Trade}.
     */
    List<Trade> findAllTrade();

    /**
     * Récupère une page de transactions en fonction des paramètres de pagination.
     *
     * @param pageable Les informations de pagination, y compris la taille de la page et le numéro de page.
     * @return Une page contenant les entités {@link Trade}.
     */
    Page<Trade> findPaginated(Pageable pageable);

    /**
     * Insère une nouvelle transaction dans la base de données.
     *
     * @param trade L'entité {@link Trade} à insérer.
     * @return L'entité {@link Trade} insérée avec un identifiant généré.
     */
    Trade insert(Trade trade);

    /**
     * Supprime une transaction existante de la base de données.
     * <p>
     * La méthode est annotée avec {@code @Transactional} pour garantir que l'opération
     * est effectuée dans une transaction unique.
     *
     * @param trade L'entité {@link Trade} à supprimer.
     */
    @Transactional
    void delete(Trade trade);

    /**
     * Recherche une transaction par son identifiant.
     *
     * @param id L'identifiant unique de la transaction.
     * @return L'entité {@link Trade} correspondante.
     * @throws IllegalArgumentException si l'entité avec l'ID donné n'existe pas.
     */
    Trade findTrade(Integer id);

    /**
     * Vérifie si une transaction existe par son identifiant.
     *
     * @param id L'identifiant unique de la transaction.
     * @return {@code true} si une transaction avec cet identifiant existe, {@code false} sinon.
     */
    boolean existsById(int id);
}
