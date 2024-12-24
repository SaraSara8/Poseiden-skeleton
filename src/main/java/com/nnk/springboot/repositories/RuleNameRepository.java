package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface pour la gestion des opérations de base de données liées aux entités {@link RuleName}.
 * <p>
 * Cette interface étend {@link JpaRepository}, offrant des méthodes CRUD (Create, Read, Update, Delete)
 * ainsi que des fonctionnalités avancées comme le tri et la pagination.
 * <p>
 * Fonctionnalités principales :
 * - Gestion des entités {@link RuleName} via des méthodes standard de JPA.
 * - Intégration avec Spring Data JPA pour la personnalisation des requêtes si nécessaire.
 *
 * <p>
 * Exemple d'utilisation :
 * <pre>
 * {@code
 * RuleName ruleName = ruleNameRepository.findById(1)
 *     .orElseThrow(() -> new IllegalArgumentException("RuleName non trouvé"));
 * }
 * </pre>
 */
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
    // Hérite des méthodes CRUD et des fonctionnalités avancées de JpaRepository.
}
