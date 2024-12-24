package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface pour la gestion des opérations métier liées aux entités {@link BidList}.
 * <p>
 * Cette interface définit les méthodes nécessaires pour gérer les soumissions (BidLists),
 * telles que la récupération, l'insertion, la suppression et la pagination.
 * <p>
 * Les implémentations de cette interface doivent encapsuler la logique métier
 * spécifique pour interagir avec les entités {@link BidList}.
 */
public interface BidListService {

    /**
     * Récupère la liste de toutes les soumissions disponibles.
     *
     * @return Une liste contenant toutes les entités {@link BidList}.
     */
    List<BidList> findAll();

    /**
     * Récupère une page de soumissions en fonction des paramètres de pagination.
     *
     * @param pageable Les informations de pagination, y compris la taille de la page et le numéro de page.
     * @return Une page contenant les entités {@link BidList}.
     */
    Page<BidList> findPaginated(Pageable pageable);

    /**
     * Recherche une soumission par son identifiant.
     *
     * @param id L'identifiant unique de la soumission.
     * @return L'entité {@link BidList} correspondante.
     * @throws IllegalArgumentException si l'entité avec l'ID donné n'existe pas.
     */
    BidList findBidList(Integer id);

    /**
     * Insère une nouvelle soumission dans la base de données.
     *
     * @param bidList L'entité {@link BidList} à insérer.
     * @return L'entité {@link BidList} insérée avec un identifiant généré.
     */
    BidList insert(BidList bidList);

    /**
     * Supprime une soumission existante de la base de données.
     *
     * @param bidList L'entité {@link BidList} à supprimer.
     */
    void delete(BidList bidList);

    /**
     * Vérifie si une soumission existe par son identifiant.
     *
     * @param id L'identifiant unique de la soumission.
     * @return {@code true} si une soumission avec cet identifiant existe, {@code false} sinon.
     */
    boolean existsById(int id);
}
