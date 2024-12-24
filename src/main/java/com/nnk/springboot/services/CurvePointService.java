package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface pour la gestion des opérations métier liées aux entités {@link CurvePoint}.
 * <p>
 * Cette interface définit les méthodes nécessaires pour gérer les points de courbe (CurvePoints),
 * telles que la récupération, l'insertion, la suppression et la pagination.
 * <p>
 * Les implémentations de cette interface doivent encapsuler la logique métier
 * spécifique pour interagir avec les entités {@link CurvePoint}.
 */
public interface CurvePointService {

    /**
     * Récupère la liste de tous les points de courbe disponibles.
     *
     * @return Une liste contenant toutes les entités {@link CurvePoint}.
     */
    List<CurvePoint> findAllCurvePoints();

    /**
     * Récupère une page de points de courbe en fonction des paramètres de pagination.
     *
     * @param pageable Les informations de pagination, y compris la taille de la page et le numéro de page.
     * @return Une page contenant les entités {@link CurvePoint}.
     */
    Page<CurvePoint> findPaginated(Pageable pageable);

    /**
     * Insère un nouveau point de courbe dans la base de données.
     *
     * @param curvePoint L'entité {@link CurvePoint} à insérer.
     * @return L'entité {@link CurvePoint} insérée avec un identifiant généré.
     */
    CurvePoint insert(CurvePoint curvePoint);

    /**
     * Supprime un point de courbe existant de la base de données.
     *
     * @param curvePoint L'entité {@link CurvePoint} à supprimer.
     */
    void delete(CurvePoint curvePoint);

    /**
     * Recherche un point de courbe par son identifiant.
     *
     * @param id L'identifiant unique du point de courbe.
     * @return L'entité {@link CurvePoint} correspondante.
     * @throws IllegalArgumentException si l'entité avec l'ID donné n'existe pas.
     */
    CurvePoint findCurvePoint(Integer id);

    /**
     * Vérifie si un point de courbe existe par son identifiant.
     *
     * @param id L'identifiant unique du point de courbe.
     * @return {@code true} si un point de courbe avec cet identifiant existe, {@code false} sinon.
     */
    boolean existsById(int id);
}
