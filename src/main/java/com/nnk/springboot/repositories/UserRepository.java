package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * Interface pour la gestion des opérations de base de données liées aux entités {@link User}.
 * <p>
 * Cette interface hérite de {@link JpaRepository} pour fournir des opérations CRUD de base,
 * et de {@link JpaSpecificationExecutor} pour des requêtes avancées avec des spécifications.
 * <p>
 * Fonctionnalités principales :
 * <ul>
 *     <li>Rechercher un utilisateur par ID ou par nom d'utilisateur.</li>
 *     <li>Récupérer tous les utilisateurs.</li>
 *     <li>Étendre les fonctionnalités grâce à {@link JpaSpecificationExecutor}.</li>
 * </ul>
 *
 * Exemple d'utilisation :
 * <pre>
 * {@code
 * Optional<User> user = userRepository.findByUsername("admin");
 * user.ifPresent(u -> System.out.println(u.getUsername()));
 * }
 * </pre>
 *
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 * @see User
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * Recherche un utilisateur par son ID.
     *
     * @param id L'ID de l'utilisateur.
     * @return Un objet {@link Optional} contenant l'utilisateur s'il existe, sinon {@link Optional} vide.
     */
    Optional<User> findById(Long id);

    /**
     * Récupère la liste de tous les utilisateurs enregistrés dans la base de données.
     *
     * @return Une liste contenant tous les utilisateurs.
     */
    List<User> findAll();

    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur.
     * @return Un objet {@link Optional} contenant l'utilisateur correspondant s'il existe, sinon {@link Optional} vide.
     */
    Optional<User> findByUsername(String username);
}
