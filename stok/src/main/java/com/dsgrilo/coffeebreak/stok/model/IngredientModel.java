package com.dsgrilo.coffeebreak.stok.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredient")
public class IngredientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID ingredient_uuid;

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String unity_measure;

    private Long amount;

    private Long unity_price;


}
