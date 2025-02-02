package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Implémentation du service User.
 * Ce service permet de gérer les opérations liées aux utilisateurs du système.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    final private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Récupère un utilisateur par son nom d'utilisateur.
     * @param username Le nom d'utilisateur.
     * @return L'utilisateur trouvé ou null si non trouvé.
     */
    @Override
    public User findByUsername(String username) {
        logger.info("Recherche de l'utilisateur avec le nom d'utilisateur : {}", username);
        return userRepository.findByUsername(username).orElse(null);
    }



    /**
     * Récupère une page paginée de soumissions.
     *
     * @param pageable l'objet définissant la pagination (page actuelle, taille de la page, etc.).
     * @return une page contenant les instances de {@link User}.
     */
    @Override
    public Page<User> findPaginated(Pageable pageable) {
        logger.info("Récupération de toutes les utilisateurs par page");
        return userRepository.findAll(pageable);
    }



    /**
     * Insère un nouvel utilisateur.
     * @param user L'utilisateur à insérer.
     * @return L'utilisateur inséré.
     */
    @Transactional
    @Override
    public User insert(User user) {
        logger.info("Insertion de l'utilisateur : {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    /**
     * Mise à jour de utilisateur.
     * @param user L'utilisateur à insérer.
     * @return L'utilisateur inséré.
     */
    @Transactional
    @Override
    public User update(User user) {
        logger.info("mise à jour de l'utilisateur : {}", user);

        if (!user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }



    /**
     * Supprime un utilisateur.
     * @param user L'utilisateur à supprimer.
     */
    @Transactional
    @Override
    public void delete(User user) {
        logger.info("Suppression de l'utilisateur avec l'ID : {}", user.getId());
        userRepository.deleteById(user.getId());
    }

    /**
     * Trouve un utilisateur par son ID.
     * @param id L'ID de l'utilisateur.
     * @return L'utilisateur trouvé ou null si non trouvé.
     */
    @Override
    public User findUser(Integer id) {
        logger.info("Recherche de l'utilisateur avec l'ID : {}", id);
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Récupère tous les utilisateurs.
     * @return La liste des utilisateurs.
     */
    @Override
    public List<User> findAllUsers() {
        logger.info("Récupération de tous les utilisateurs");
        return userRepository.findAll();
    }

    /**
     * Vérifie si un utilisateur existe par son ID.
     * @param id L'ID de l'utilisateur.
     * @return true si l'utilisateur existe, false sinon.
     */
    @Override
    public boolean existsById(int id) {
        logger.info("Vérification de l'existence de l'utilisateur avec l'ID : {}", id);
        return userRepository.existsById(id);
    }
}
