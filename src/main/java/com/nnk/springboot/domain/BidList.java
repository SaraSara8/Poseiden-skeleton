package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Représente une entité "BidList" dans la base de données.
 * Cette classe contient les informations concernant une liste d'offres de vente/achat.
 */
@Data
@Entity
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String type;

    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;

    private String benchmark;

    private Timestamp bidListDate;

    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;

    private Timestamp creationDate;

    private String revisionName;

    private Timestamp revisionDate;

    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    /**
     * Constructeur par défaut.
     */
    public BidList() {

    }
}
