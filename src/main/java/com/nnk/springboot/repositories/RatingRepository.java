package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface pour la gestion des opérations de base de données liées aux entités {@link Rating}.
 * <p>
 * Cette interface étend {@link JpaRepository}, offrant des méthodes CRUD (Create, Read, Update, Delete)
 * et des fonctionnalités avancées comme le tri et la pagination.
 * <p>
 * Fonctionnalités principales :
 * - Gestion des transactions pour les entités {@link Rating}.
 * - Intégration facile avec Spring Data JPA pour des requêtes personnalisées.
 * - Support pour des opérations de tri et de recherche si nécessaire.
 *
 * <p>
 * Exemple d'utilisation :
 * <pre>
 * {@code
 * Rating rating = ratingRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("Rating not found"));
 * }
 * </pre>
 */
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    // Hérite des méthodes CRUD et des fonctionnalités avancées de JpaRepository.
}
