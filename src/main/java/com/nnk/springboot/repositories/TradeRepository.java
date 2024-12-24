package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface pour la gestion des opérations de base de données liées aux entités {@link Trade}.
 * <p>
 * Cette interface hérite de {@link JpaRepository}, offrant des fonctionnalités standard pour les opérations CRUD
 * (Create, Read, Update, Delete) ainsi que des fonctionnalités avancées comme le tri et la pagination.
 * <p>
 * Fonctionnalités principales :
 * <ul>
 *     <li>Gestion des entités {@link Trade} via des méthodes standard de Spring Data JPA.</li>
 *     <li>Possibilité d'étendre avec des méthodes de requêtes personnalisées si nécessaire.</li>
 * </ul>
 *
 * <p>
 * Exemple d'utilisation :
 * <pre>
 * {@code
 * Trade trade = tradeRepository.findById(1)
 *     .orElseThrow(() -> new IllegalArgumentException("Trade non trouvé"));
 * }
 * </pre>
 *
 * @see JpaRepository
 * @see Trade
 */
public interface TradeRepository extends JpaRepository<Trade, Integer> {
    // Hérite des méthodes CRUD et fonctionnalités avancées de JpaRepository.
}
