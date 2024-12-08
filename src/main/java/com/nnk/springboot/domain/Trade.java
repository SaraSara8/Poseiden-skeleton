package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Représente une entité "Trade" dans la base de données.
 * Cette classe contient les informations relatives à une transaction ou un trade dans le système.
 */
@Data
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;

    private String benchmark;

    private Timestamp tradeDate;

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
    public Trade() {

    }
}
