package com.dsgrilo.coffeebreak.stok.controller;


import com.dsgrilo.coffeebreak.stok.model.ProductModel;
import com.dsgrilo.coffeebreak.stok.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductModel> createProduct(@RequestBody @Valid  ProductModel productModel)
    {
            ProductModel product = productModel;




            return ResponseEntity.ok().body(productRepository.save(productModel));


    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductModel>> findAllProducts(){

            List<ProductModel> products = productRepository.findAll();

            return  ResponseEntity.ok().body(products);
    }





}
