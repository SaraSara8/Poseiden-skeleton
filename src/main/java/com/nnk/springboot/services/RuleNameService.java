package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface pour la gestion des opérations métier liées aux entités {@link RuleName}.
 * <p>
 * Cette interface définit les méthodes nécessaires pour gérer les règles métier (RuleName),
 * incluant la récupération, l'insertion, la suppression et la pagination.
 * <p>
 * Les implémentations de cette interface doivent encapsuler la logique métier
 * spécifique pour interagir avec les entités {@link RuleName}.
 */
public interface RuleNameService {

    /**
     * Récupère la liste de toutes les règles métier disponibles.
     *
     * @return Une liste contenant toutes les entités {@link RuleName}.
     */
    List<RuleName> findAllRuleNames();

    /**
     * Récupère une page de règles métier en fonction des paramètres de pagination.
     *
     * @param pageable Les informations de pagination, y compris la taille de la page et le numéro de page.
     * @return Une page contenant les entités {@link RuleName}.
     */
    Page<RuleName> findPaginated(Pageable pageable);

    /**
     * Insère une nouvelle règle métier dans la base de données.
     *
     * @param ruleName L'entité {@link RuleName} à insérer.
     * @return L'entité {@link RuleName} insérée avec un identifiant généré.
     */
    RuleName insert(RuleName ruleName);

    /**
     * Supprime une règle métier existante de la base de données.
     *
     * @param ruleName L'entité {@link RuleName} à supprimer.
     */
    void delete(RuleName ruleName);

    /**
     * Recherche une règle métier par son identifiant.
     *
     * @param id L'identifiant unique de la règle métier.
     * @return L'entité {@link RuleName} correspondante.
     * @throws IllegalArgumentException si l'entité avec l'ID donné n'existe pas.
     */
    RuleName findRuleName(Integer id);

    /**
     * Vérifie si une règle métier existe par son identifiant.
     *
     * @param id L'identifiant unique de la règle métier.
     * @return {@code true} si une règle métier avec cet identifiant existe, {@code false} sinon.
     */
    boolean existsById(int id);
}
