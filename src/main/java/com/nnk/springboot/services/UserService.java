package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface pour la gestion des opérations métier liées aux utilisateurs ({@link User}).
 * <p>
 * Cette interface définit les méthodes nécessaires pour gérer les entités {@link User},
 * incluant la création, la mise à jour, la suppression, la pagination, et la recherche par identifiant ou nom d'utilisateur.
 */
public interface UserService {

    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur de l'entité {@link User}.
     * @return L'utilisateur correspondant au nom fourni.
     * @throws IllegalArgumentException si aucun utilisateur n'est trouvé.
     */
    User findByUsername(String username);

    /**
     * Récupère une page d'utilisateurs en fonction des paramètres de pagination.
     *
     * @param pageable Les informations de pagination, y compris la taille de la page et le numéro de page.
     * @return Une page contenant les entités {@link User}.
     */
    Page<User> findPaginated(Pageable pageable);

    /**
     * Insère un nouvel utilisateur dans la base de données.
     *
     * @param user L'entité {@link User} à insérer.
     * @return L'entité {@link User} insérée avec un identifiant généré.
     */
    User insert(User user);

    /**
     * Mise à jour d'un utilisateur dans la base de données.
     *
     * @param user L'entité {@link User} à insérer.
     * @return L'entité {@link User} insérée avec un identifiant généré.
     */
    public User update(User user);

    /**
     * Récupère la liste de tous les utilisateurs.
     *
     * @return Une liste contenant toutes les entités {@link User}.
     */
    List<User> findAllUsers();

    /**
     * Supprime un utilisateur existant de la base de données.
     * <p>
     * Cette méthode est annotée avec {@code @Transactional} pour garantir que l'opération
     * est effectuée dans une transaction unique.
     *
     * @param user L'entité {@link User} à supprimer.
     */
    @Transactional
    void delete(User user);

    /**
     * Recherche un utilisateur par son identifiant unique.
     *
     * @param id L'identifiant unique de l'utilisateur.
     * @return L'entité {@link User} correspondante.
     * @throws IllegalArgumentException si l'entité avec l'ID donné n'existe pas.
     */
    User findUser(Integer id);

    /**
     * Vérifie si un utilisateur existe par son identifiant.
     *
     * @param id L'identifiant unique de l'utilisateur.
     * @return {@code true} si un utilisateur avec cet identifiant existe, {@code false} sinon.
     */
    boolean existsById(int id);
}
