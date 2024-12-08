package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service qui gère les opérations liées aux listes de soumissions ("BidList").
 * Ce service contient des méthodes pour insérer, supprimer, rechercher et vérifier l'existence d'une soumission.
 */
@Service
public class BidListServiceImpl implements BidListService {

    private static final Logger logger = LoggerFactory.getLogger(BidListServiceImpl.class);

    private final BidListRepository bidListRepository;

    /**
     * Constructeur de BidListServiceImpl.
     *
     * @param bidListRepository le repository utilisé pour l'accès aux données de BidList
     */
    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * Recherche de toutes les soumissions (BidLists).
     *
     * @return la liste de toutes les soumissions
     */
    @Override
    public List<BidList> findAll() {
        logger.info("Recherche de toutes les soumissions.");
        return bidListRepository.findAll();
    }

    /**
     * Recherche une soumission par son identifiant.
     *
     * @param id l'identifiant de la soumission
     * @return la soumission correspondante, ou une exception si elle n'existe pas
     * @throws IllegalArgumentException si l'identifiant de la soumission est invalide
     */
    @Override
    public BidList findBidList(Integer id) {
        logger.info("Recherche de la soumission avec l'ID : {}", id);
        return bidListRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "ID de soumission invalide: " + id;
                    logger.error(errorMsg);
                    return new IllegalArgumentException(errorMsg);
                });
    }

    /**
     * Insère une nouvelle soumission dans la base de données.
     *
     * @param bidList l'objet BidList à insérer
     * @return l'objet BidList inséré
     */
    @Transactional
    @Override
    public BidList insert(BidList bidList) {
        logger.info("Insertion de la soumission : {}", bidList);
        return bidListRepository.save(bidList);
    }

    /**
     * Supprime une soumission de la base de données.
     *
     * @param bidList l'objet BidList à supprimer
     */
    @Transactional
    @Override
    public void delete(BidList bidList) {
        logger.info("Suppression de la soumission avec l'ID : {}", bidList.getId());
        bidListRepository.deleteById(bidList.getId());
    }

    /**
     * Vérifie si une soumission existe par son identifiant.
     *
     * @param id l'identifiant de la soumission
     * @return true si la soumission existe, false sinon
     */
    @Override
    public boolean existsById(int id) {
        logger.info("Vérification de l'existence de la soumission avec l'ID : {}", id);
        return bidListRepository.existsById(id);
    }
}
