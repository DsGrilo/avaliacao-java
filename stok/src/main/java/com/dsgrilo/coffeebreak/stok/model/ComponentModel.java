package com.dsgrilo.coffeebreak.stok.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.cdi.Eager;

import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Setter
@Getter
public class ComponentModel {

    @Id
    private UUID component_id = UUID.randomUUID();

    @NotBlank
    private String ingredient_id;

    @NotNull
    private float ingredient_amount;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "component_idFK")
    private ProductModel productModel;


}
