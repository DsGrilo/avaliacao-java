package com.dsgrilo.coffeebreak.stok.controller;


import com.dsgrilo.coffeebreak.stok.exceptions.ResourceNotFoundException;
import com.dsgrilo.coffeebreak.stok.model.ComponentModel;
import com.dsgrilo.coffeebreak.stok.model.ProductModel;
import com.dsgrilo.coffeebreak.stok.repository.IngredientRepository;
import com.dsgrilo.coffeebreak.stok.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private final ProductRepository productRepository;
    private final IngredientRepository ingredientRepository;

    public ProductController(ProductRepository productRepository, IngredientRepository ingredientRepository) {
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
    }


    @PostMapping("/create")
    public ResponseEntity createProduct(@RequestBody @Valid ProductModel productModel) throws ResourceNotFoundException {


        float costProduct = 0;

        for (ComponentModel ingredients: productModel.getComponents()) {

                var id = ingredients.getIngredient_id();
                var ingredient = ingredientRepository.getById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Ingrediente não cadastrado")
                );

                var price  = ingredient.getUnity_price();
                var amount = ingredient.getAmount();

                var costIngredient = price + amount ;

                costProduct += costIngredient;

        }


            productModel.setCostProduct(costProduct);
            ProductModel product = productRepository.save(productModel);



            return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }


    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductModel>> findAllProducts(){

            List<ProductModel> products = productRepository.findAll();

            return  ResponseEntity.ok().body(products);
    }


    @GetMapping("/find/{name}")
    public ResponseEntity findById(@PathVariable("name") @Valid String name) throws ResourceNotFoundException {
        ProductModel productModel = productRepository.findByName(name).orElseThrow(() ->
                new ResourceNotFoundException("Produto não encontrado"));

        return ResponseEntity.ok().body(productModel);
    }

    @PostMapping("/image")
    public ResponseEntity<HttpStatus> uploadImage(@RequestParam String name,@RequestBody MultipartFile image) throws ResourceNotFoundException {

        ProductModel productModel = productRepository.findByName(name).orElseThrow(() ->
                new ResourceNotFoundException("Ingrediente não cadastrado"));

        try {

            byte[] images = image.getBytes();

            productModel.setImages(images);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(HttpStatus.ACCEPTED);

    }

    @GetMapping("/verify/{name}")
    public ResponseEntity verifyProduct(@PathVariable("name") String name) throws ResourceNotFoundException {
        ProductModel productModel = productRepository.findByName(name).orElseThrow(() ->
                new ResourceNotFoundException("Ingrediente não cadastrado"));

        for (ComponentModel component: productModel.getComponents())
        {
             var id_ingrediet = component.getIngredient_id();

            var ingredient = ingredientRepository.getById(id_ingrediet).orElseThrow(() ->
                    new ResourceNotFoundException("Ingrediente não cadastrado")
            );

            if(ingredient.getAmount() == 0){
                return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(HttpStatus.INSUFFICIENT_STORAGE);
            }

        }

        return ResponseEntity.ok().body(HttpStatus.OK);
    }



}
