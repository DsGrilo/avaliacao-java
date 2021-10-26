package com.dsgrilo.coffeebreak.stok.repository;

import com.dsgrilo.coffeebreak.stok.model.IngredientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientModel, UUID> {



}
