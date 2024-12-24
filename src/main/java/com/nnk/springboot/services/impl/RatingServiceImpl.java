package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Implémentation du service Rating.
 * Ce service permet de gérer les opérations liées aux notations de crédit.
 */
@Service
public class RatingServiceImpl implements RatingService {

    private static final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Récupère toutes les notations.
     * @return la liste des notations.
     */
    public List<Rating> findAllRating(){
        logger.info("Récupération de toutes les notations");
        return ratingRepository.findAll();
    }



    /**
     * Récupère une page paginée de soumissions.
     *
     * @param pageable l'objet définissant la pagination (page actuelle, taille de la page, etc.).
     * @return une page contenant les instances de {@link Rating}.
     */
    @Override
    public Page<Rating> findPaginated(Pageable pageable) {
        logger.info("Récupération de toutes les notations par page");
        return ratingRepository.findAll(pageable);
    }


    /**
     * Insère une nouvelle notation.
     * @param rating La notation à insérer.
     * @return La notation insérée.
     */
    public Rating insert(Rating rating){
        logger.info("Insertion de la notation : {}", rating);
        return ratingRepository.save(rating);
    }

    /**
     * Supprime une notation.
     * @param rating La notation à supprimer.
     */
    public void delete(Rating rating){
        logger.info("Suppression de la notation avec l'ID : {}", rating.getId());
        ratingRepository.deleteById(rating.getId());
    }

    /**
     * Trouve une notation par son ID.
     * @param id L'ID de la notation.
     * @return La notation trouvée ou null si non trouvée.
     */
    public Rating findRating(Integer id){
        logger.info("Recherche de la notation avec l'ID : {}", id);
        return ratingRepository.findById(id).orElse(null);
    }

    /**
     * Vérifie si une notation existe par son ID.
     * @param id L'ID de la notation.
     * @return true si la notation existe, false sinon.
     */
    @Override
    public boolean existsById(int id) {
        logger.info("Vérification de l'existence de la notation avec l'ID : {}", id);
        return ratingRepository.existsById(id);
    }
}
