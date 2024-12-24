package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation du service gérant les opérations liées aux objets "BidList".
 *
 * Ce service fournit des fonctionnalités pour :
 * - Rechercher toutes les soumissions ou une seule soumission par son identifiant.
 * - Insérer une nouvelle soumission dans la base de données.
 * - Supprimer une soumission existante.
 * - Vérifier l'existence d'une soumission.
 * - Gérer la pagination des soumissions.
 *
 * Ce service utilise un {@link BidListRepository} pour accéder aux données dans la base de données.
 */
@Service
public class BidListServiceImpl implements BidListService {

    private static final Logger logger = LoggerFactory.getLogger(BidListServiceImpl.class);

    private final BidListRepository bidListRepository;

    /**
     * Constructeur du service BidListServiceImpl.
     *
     * @param bidListRepository le repository utilisé pour effectuer des opérations sur les données des soumissions.
     */
    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * Récupère la liste de toutes les soumissions disponibles dans la base de données.
     *
     * @return une liste contenant toutes les instances de {@link BidList}.
     */
    @Override
    public List<BidList> findAll() {
        logger.info("Recherche de toutes les soumissions.");
        return bidListRepository.findAll();
    }

    /**
     * Récupère une page paginée de soumissions.
     *
     * @param pageable l'objet définissant la pagination (page actuelle, taille de la page, etc.).
     * @return une page contenant les instances de {@link BidList}.
     */
    @Override
    public Page<BidList> findPaginated(Pageable pageable) {
        logger.info("Recherche de toutes les soumissions par page.");
        return bidListRepository.findAll(pageable);
    }

    /**
     * Recherche une soumission par son identifiant.
     *
     * @param id l'identifiant unique de la soumission à rechercher.
     * @return l'objet {@link BidList} correspondant à l'identifiant.
     * @throws IllegalArgumentException si aucune soumission n'est trouvée pour l'identifiant donné.
     */
    @Override
    public BidList findBidList(Integer id) {
        logger.info("Recherche de la soumission avec l'ID : {}", id);
        return bidListRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "ID de soumission invalide : " + id;
                    logger.error(errorMsg);
                    return new IllegalArgumentException(errorMsg);
                });
    }

    /**
     * Insère une nouvelle soumission dans la base de données.
     *
     * @param bidList l'objet {@link BidList} à insérer.
     * @return l'objet {@link BidList} après insertion avec son identifiant généré.
     */
    @Transactional
    @Override
    public BidList insert(BidList bidList) {
        logger.info("Insertion de la soumission : {}", bidList);
        return bidListRepository.save(bidList);
    }

    /**
     * Supprime une soumission existante de la base de données.
     *
     * @param bidList l'objet {@link BidList} à supprimer.
     */
    @Transactional
    @Override
    public void delete(BidList bidList) {
        logger.info("Suppression de la soumission avec l'ID : {}", bidList.getBidListDate());
        bidListRepository.deleteById(bidList.getBidListId());
    }

    /**
     * Vérifie si une soumission existe dans la base de données en utilisant son identifiant.
     *
     * @param id l'identifiant unique de la soumission.
     * @return {@code true} si la soumission existe, sinon {@code false}.
     */
    @Override
    public boolean existsById(int id) {
        logger.info("Vérification de l'existence de la soumission avec l'ID : {}", id);
        return bidListRepository.existsById(id);
    }
}
