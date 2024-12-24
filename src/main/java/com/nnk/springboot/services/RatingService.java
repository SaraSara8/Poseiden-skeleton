package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface pour la gestion des opérations métier liées aux entités {@link Rating}.
 * <p>
 * Cette interface définit les méthodes nécessaires pour gérer les notations (Ratings),
 * incluant la récupération, l'insertion, la suppression et la pagination.
 * <p>
 * Les implémentations de cette interface doivent encapsuler la logique métier
 * spécifique pour interagir avec les entités {@link Rating}.
 */
public interface RatingService {

    /**
     * Récupère la liste de toutes les notations disponibles.
     *
     * @return Une liste contenant toutes les entités {@link Rating}.
     */
    List<Rating> findAllRating();

    /**
     * Récupère une page de notations en fonction des paramètres de pagination.
     *
     * @param pageable Les informations de pagination, y compris la taille de la page et le numéro de page.
     * @return Une page contenant les entités {@link Rating}.
     */
    Page<Rating> findPaginated(Pageable pageable);

    /**
     * Insère une nouvelle notation dans la base de données.
     *
     * @param rating L'entité {@link Rating} à insérer.
     * @return L'entité {@link Rating} insérée avec un identifiant généré.
     */
    Rating insert(Rating rating);

    /**
     * Supprime une notation existante de la base de données.
     *
     * @param rating L'entité {@link Rating} à supprimer.
     */
    void delete(Rating rating);

    /**
     * Recherche une notation par son identifiant.
     *
     * @param id L'identifiant unique de la notation.
     * @return L'entité {@link Rating} correspondante.
     * @throws IllegalArgumentException si l'entité avec l'ID donné n'existe pas.
     */
    Rating findRating(Integer id);

    /**
     * Vérifie si une notation existe par son identifiant.
     *
     * @param id L'identifiant unique de la notation.
     * @return {@code true} si une notation avec cet identifiant existe, {@code false} sinon.
     */
    boolean existsById(int id);
}
