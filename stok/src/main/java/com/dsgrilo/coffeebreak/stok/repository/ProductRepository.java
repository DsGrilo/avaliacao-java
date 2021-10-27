package com.dsgrilo.coffeebreak.stok.repository;

import com.dsgrilo.coffeebreak.stok.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

        ProductModel findByName(String name);

}
