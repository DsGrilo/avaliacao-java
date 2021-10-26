package com.dsgrilo.coffeebreak.stok.controller;


import com.dsgrilo.coffeebreak.stok.model.IngredientModel;
import com.dsgrilo.coffeebreak.stok.repository.IngredientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @PostMapping("/create")
    private ResponseEntity<IngredientModel> createIngredient (@RequestBody IngredientModel ingredientModel)
    {

        return ResponseEntity.ok().body(ingredientRepository.save(ingredientModel));

    }

}
