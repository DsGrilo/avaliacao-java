package com.dsgrilo.coffeebreak.stok.model;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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


  //  private char[] image;

    @NotBlank(message = "Preenchimento do Nome Obrigatorio")
    private String name;

    @NotBlank(message = "Tipo de Medida Obtigatória")
    private String unity_measure;

    private float unity_price ;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ComponentModel> components;
    

}
