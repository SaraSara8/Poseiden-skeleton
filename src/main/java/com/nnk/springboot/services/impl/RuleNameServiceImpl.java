package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Implémentation du service RuleName.
 * Ce service permet de gérer les règles définies dans le système.
 */
@Service
public class RuleNameServiceImpl implements RuleNameService {

    private static final Logger logger = LoggerFactory.getLogger(RuleNameServiceImpl.class);

    private final RuleNameRepository ruleNameRepository;

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * Récupère toutes les règles de nom.
     * @return la liste des règles de nom.
     */
    public List<RuleName> findAllRuleNames() {
        logger.info("Récupération de toutes les règles de nom");
        return ruleNameRepository.findAll();
    }


    /**
     * Récupère une page paginée de soumissions.
     *
     * @param pageable l'objet définissant la pagination (page actuelle, taille de la page, etc.).
     * @return une page contenant les instances de {@link RuleName}.
     */
    @Override
    public Page<RuleName> findPaginated(Pageable pageable) {
        logger.info("Récupération de toutes les règles de nom par page");
        return ruleNameRepository.findAll(pageable);
    }



    /**
     * Insère une nouvelle règle de nom.
     * @param ruleName La règle de nom à insérer.
     * @return La règle de nom insérée.
     */
    public RuleName insert(RuleName ruleName) {
        logger.info("Insertion de la règle de nom : {}", ruleName);
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Supprime une règle de nom.
     * @param ruleName La règle de nom à supprimer.
     */
    public void delete(RuleName ruleName) {
        logger.info("Suppression de la règle de nom avec l'ID : {}", ruleName.getId());
        ruleNameRepository.deleteById(ruleName.getId());
    }

    /**
     * Trouve une règle de nom par son ID.
     * @param id L'ID de la règle de nom.
     * @return La règle de nom trouvée ou null si non trouvée.
     */
    public RuleName findRuleName(Integer id) {
        logger.info("Recherche de la règle de nom avec l'ID : {}", id);
        return ruleNameRepository.findById(id).orElse(null);
    }

    /**
     * Vérifie si une règle de nom existe par son ID.
     * @param id L'ID de la règle de nom.
     * @return true si la règle de nom existe, false sinon.
     */
    @Override
    public boolean existsById(int id) {
        logger.info("Vérification de l'existence de la règle de nom avec l'ID : {}", id);
        return ruleNameRepository.existsById(id);
    }
}
