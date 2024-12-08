package com.nnk.springboot.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Représente un point de courbe (CurvePoint) dans la base de données.
 * Cette classe contient les informations concernant un point d'une courbe de valeur.
 */
@Data
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "curve_id")
    private Integer curveId;

    @Column(name = "as_of_date")
    private Timestamp asOfDate;

    private double term;
    private double value;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    /**
     * Constructeur par défaut.
     */
    public CurvePoint() {
    }
}
