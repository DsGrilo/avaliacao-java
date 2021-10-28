package com.dsgrilo.coffeebreak.stok.controller;


import com.dsgrilo.coffeebreak.stok.exceptions.ResourceNotFoundException;
import com.dsgrilo.coffeebreak.stok.model.IngredientModel;
import com.dsgrilo.coffeebreak.stok.repository.IngredientRepository;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private final IngredientRepository ingredientRepository;

    // Inserido modelo de entidade para utilizar a inversão de dependencia
    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<IngredientModel> findById(@PathVariable("id") String id) throws ResourceNotFoundException {

        // busca no banco o ingrediente pelo ID e caso nulo retorna exceção
        IngredientModel ingredient = ingredientRepository.getById(id).orElseThrow(() ->
                new ResourceNotFoundException("Ingrediente não encontrado"));

        //retorna um OK com o ingrediente
        return ResponseEntity.ok().body(ingredient);

    }

    @GetMapping("/find")
    private ResponseEntity<List<IngredientModel>> findAllIngredients(){
        // faz a busca de todos os ingredientes no banco
        return ResponseEntity.ok().body(ingredientRepository.findAll());
    }

    @PostMapping("/create") // @Valid faz a validação da corpo da requisição
    public ResponseEntity createIngredient (@RequestBody @Valid IngredientModel ingredientModel)
    {

        if(ingredientRepository.getById(ingredientModel.getId()).isPresent()){
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        // salva no banco o ingrediente recebido na requisição
        IngredientModel ingredient = ingredientRepository.save(ingredientModel);


        //retorna status 201 e no corpo o ingrediente
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredient);

    }

    @PutMapping("/inventory/{id}")
    public ResponseEntity<IngredientModel> updateInventory(@PathVariable("id") String id,
                                                      @RequestBody @Valid IngredientModel ingredientModel  ) throws ResourceNotFoundException {

         IngredientModel ingredient = ingredientRepository.getById(id).orElseThrow(() ->
                 new ResourceNotFoundException("Ingrediente não encontrado"));

        IngredientModel body = ingredientModel;

        // pega a quantidade que tem no ingrediente cadastrado no banco e troca pela quantidade do ingrediente
        // que esta na requisição
        ingredient.setAmount(body.getAmount());

        final IngredientModel updatedIngredient = ingredientRepository.save(ingredient);

        return ResponseEntity.ok().body(updatedIngredient);
    }




    @PutMapping("update/{id}")
    public ResponseEntity<IngredientModel> updateIngredient(@PathVariable("id") String id,
                                                      @RequestBody @Valid IngredientModel ingredientModel  ) throws Exception
    {
            try{
                IngredientModel updateIngredient = ingredientRepository.getById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Ingrediente não encontrado"));

                updateIngredient.setId(ingredientModel.getId());
                updateIngredient.setAmount(ingredientModel.getAmount());
                updateIngredient.setName(ingredientModel.getName());
                updateIngredient.setUnity_measure(ingredientModel.getUnity_measure());

                final IngredientModel updatedIngredient = ingredientRepository.save(updateIngredient);

                return ResponseEntity.ok().body(updatedIngredient);
            }catch (KeyAlreadyExistsException exception){
                throw new KeyAlreadyExistsException("ID ja utilizado em outro ingrediente");
            }

    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteIngrediente(String id) throws ResourceNotFoundException {
        // apaga o ingrediente pelo ID
        ingredientRepository.getById(id).orElseThrow(() ->
                new ResourceNotFoundException("Ingrediente não encontrado"));

        ingredientRepository.deleteById(id);

        return ResponseEntity.ok().body(HttpStatus.ACCEPTED);

    }


}
