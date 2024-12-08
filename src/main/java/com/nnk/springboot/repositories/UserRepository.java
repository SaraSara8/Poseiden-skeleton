package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {


    /**
     * Recherche un utilisateur par son ID.
     *
     * @param id L'ID de l'utilisateur.
     * @return Un objet Optional contenant l'utilisateur s'il existe, sinon Optional vide.
     */
    Optional<User> findById(Long id);

    /**
     * Récupère la liste des tous les utilisateurs.
     *
     * @return La liste des tous les utilisateurs.
     */
    List<User> findAll();

    Optional<User> findByUsername(String username);
}