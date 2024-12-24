package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface pour la gestion des opérations de base de données liées aux entités {@link CurvePoint}.
 * <p>
 * Cette interface étend {@link JpaRepository}, offrant des méthodes CRUD (Create, Read, Update, Delete)
 * et des fonctionnalités avancées comme le tri et la pagination.
 * <p>
 * Fonctionnalités principales :
 * - Gestion des transactions pour les entités {@link CurvePoint}.
 * - Intégration facile avec Spring Data JPA pour des requêtes personnalisées.
 * - Support pour la pagination et le tri si nécessaire.
 */
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {
    // Hérite des méthodes de JpaRepository pour manipuler les entités CurvePoint.
}
