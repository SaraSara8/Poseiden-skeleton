package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Implémentation du service CurvePoint.
 * Ce service permet de gérer les opérations sur les points de courbe.
 */
@Service
public class CurvePointServiceImpl implements CurvePointService {

    private static final Logger logger = LoggerFactory.getLogger(CurvePointServiceImpl.class);

    private final CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Récupère tous les points de courbe.
     * @return la liste des points de courbe.
     */
    @Override
    public List<CurvePoint> findAllCurvePoints() {
        logger.info("Récupération de tous les points de courbe");
        return curvePointRepository.findAll();
    }



    /**
     * Récupère une page paginée de soumissions.
     *
     * @param pageable l'objet définissant la pagination (page actuelle, taille de la page, etc.).
     * @return une page contenant les instances de {@link CurvePoint}.
     */
    @Override
    public Page<CurvePoint> findPaginated(Pageable pageable) {
        logger.info("Récupération de tous les points de courbe par page");
        return curvePointRepository.findAll(pageable);
    }


    /**
     * Insère un nouveau point de courbe.
     * @param curvePoint Le point de courbe à insérer.
     * @return Le point de courbe inséré.
     */
    @Transactional
    @Override
    public CurvePoint insert(CurvePoint curvePoint) {
        logger.info("Insertion du point de courbe : {}", curvePoint);
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Supprime un point de courbe.
     * @param curvePoint Le point de courbe à supprimer.
     */
    @Transactional
    @Override
    public void delete(CurvePoint curvePoint){
        logger.info("Suppression du point de courbe avec l'ID : {}", curvePoint.getId());
        curvePointRepository.deleteById(curvePoint.getId());
    }

    /**
     * Trouve un point de courbe par son ID.
     * @param id L'ID du point de courbe.
     * @return Le point de courbe trouvé ou null si non trouvé.
     */
    @Override
    public CurvePoint findCurvePoint(Integer id){
        logger.info("Recherche du point de courbe avec l'ID : {}", id);
        return curvePointRepository.findById(id).orElse(null);
    }

    /**
     * Vérifie si un point de courbe existe par son ID.
     * @param id L'ID du point de courbe.
     * @return true si le point de courbe existe, false sinon.
     */
    @Override
    public boolean existsById(int id) {
        logger.info("Vérification de l'existence du point de courbe avec l'ID : {}", id);
        return curvePointRepository.existsById(id);
    }
}
