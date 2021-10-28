package com.dsgrilo.coffeebreak.stok.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID ingredient_uuid;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String unity_measure;


    @NotNull
    @Column(nullable = false)
    private Float amount;

    @NotNull
    @Column(nullable = false)
    private Float unity_price;



}
