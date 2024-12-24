package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Représente une entité "User" dans la base de données.
 * Cette classe contient les informations relatives à un utilisateur du système.
 */
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    private String role;

    /**
     * Récupère l'identifiant de l'utilisateur.
     *
     * @return L'identifiant de l'utilisateur.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'utilisateur.
     *
     * @param id L'identifiant à définir.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Récupère le nom d'utilisateur.
     *
     * @return Le nom d'utilisateur.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Définit le nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur à définir.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Récupère le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe de l'utilisateur.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     *
     * @param password Le mot de passe à définir.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Récupère le nom complet de l'utilisateur.
     *
     * @return Le nom complet de l'utilisateur.
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Définit le nom complet de l'utilisateur.
     *
     * @param fullname Le nom complet à définir.
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Récupère le rôle de l'utilisateur.
     *
     * @return Le rôle de l'utilisateur.
     */
    public String getRole() {
        return role;
    }

    /**
     * Définit le rôle de l'utilisateur.
     *
     * @param role Le rôle à définir.
     */
    public void setRole(String role) {
        this.role = role;
    }
}
