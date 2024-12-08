package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;

import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Représente une entité "Rating" dans la base de données.
 * Cette classe contient les informations relatives aux notations des titres financiers.
 */
@Data
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank(message = "Moodys rating is mandatory")
    private String moodysRating;

    @NotBlank(message = "SandP rating is mandatory")
    private String sandPRating;

    @NotBlank(message = "Fitch rating is mandatory")
    private String fitchRating;

    @Positive(message = "Order number must be positive")
    private Integer orderNumber;

    /**
     * Constructeur par défaut.
     */
    public Rating() {

    }
}
