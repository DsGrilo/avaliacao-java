package com.dsgrilo.coffeebreak.stok.model;


import lombok.*;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, nullable = false)
    private UUID uuid_product;


    @NotBlank(message = "Preenchimento do Nome Obrigatorio")
    private String name;

    @NotBlank(message = "Tipo de Medida Obtigatória")
    private String unity_measure;

    private float unity_price;

    @OneToMany(mappedBy = "productModel",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ComponentModel> components;



}
