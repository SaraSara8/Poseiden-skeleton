package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface pour la gestion des opérations de base de données liées aux entités {@link BidList}.
 * <p>
 * Cette interface étend {@link JpaRepository}, fournissant des méthodes CRUD (Create, Read, Update, Delete)
 * et d'autres fonctionnalités comme la pagination.
 * <p>
 * Fonctionnalités principales :
 * - Gestion automatique des transactions.
 * - Support pour la pagination et le tri.
 * - Extension facile pour des requêtes personnalisées.
 */
public interface BidListRepository extends JpaRepository<BidList, Integer> {

    /**
     * Récupère toutes les {@link BidList} avec prise en charge de la pagination.
     *
     * @param pageable Un objet {@link Pageable} spécifiant la taille de la page, le numéro de page et le tri.
     * @return Un objet {@link Page} contenant une liste paginée de {@link BidList}.
     */
    Page<BidList> findAll(Pageable pageable);
}
