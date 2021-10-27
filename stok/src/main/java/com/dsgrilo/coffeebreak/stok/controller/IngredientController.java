package com.dsgrilo.coffeebreak.stok.controller;


import com.dsgrilo.coffeebreak.stok.exceptions.ResourceNotFound;
import com.dsgrilo.coffeebreak.stok.model.IngredientModel;
import com.dsgrilo.coffeebreak.stok.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<IngredientModel> findById(@PathVariable("id") String id) throws ResourceNotFound{

        IngredientModel ingredient = ingredientRepository.getById(id);

        return ResponseEntity.ok().body(ingredient);

    }

    @GetMapping("/find")
    private ResponseEntity<List<IngredientModel>> findAllIngredients(){
        return ResponseEntity.ok().body(ingredientRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<IngredientModel> createIngredient (@RequestBody IngredientModel ingredientModel)
    {

        IngredientModel ingredient = ingredientRepository.save(ingredientModel);

        return ResponseEntity.ok().body(ingredient);

    }

    @PutMapping("/inventory/{id}")
    public ResponseEntity<IngredientModel> updateInventory(@PathVariable("id") String id,
                                                      @RequestBody @Valid IngredientModel ingredientModel  )
    {

        IngredientModel ingredient = ingredientRepository.getById(id);

        IngredientModel body = ingredientModel;

        ingredient.setAmount(body.getAmount());

        final IngredientModel updatedIngredient = ingredientRepository.save(ingredient);

        return ResponseEntity.ok().body(updatedIngredient);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<IngredientModel> updateIngredient(@PathVariable("id") String id,
                                                      @RequestBody @Valid IngredientModel ingredientModel  ) throws ResourceNotFound
    {

            IngredientModel ingredient = ingredientRepository.getById(id);

            if(ingredient == null){
                throw new ResourceNotFound("Erro ao consultar banco de dados. Confira o id do ingrediente");
            }

            final IngredientModel updatedIngredient = ingredientRepository.save(ingredient);

            return ResponseEntity.ok().body(updatedIngredient);



    }


}
