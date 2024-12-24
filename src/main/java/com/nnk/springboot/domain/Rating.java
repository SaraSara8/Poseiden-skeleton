package com.nnk.springboot.domain;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "moodys_rating")
    //@NotBlank(message = "Moodys rating is mandatory")
    private String moodysRating;

    @Column(name = "sand_p_rating")
    //@NotBlank(message = "SandP rating is mandatory")
    private String sandPRating;

    @Column(name = "fitch_rating")
    //@NotBlank(message = "Fitch rating is mandatory")
    private String fitchRating;

    @Column(name = "order_number")
    @Positive(message = "Order number must be positive")
    private Integer orderNumber;

}
