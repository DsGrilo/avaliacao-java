package com.dsgrilo.coffeebreak.stok.controller;


import com.dsgrilo.coffeebreak.stok.model.ComponentModel;
import com.dsgrilo.coffeebreak.stok.model.ProductModel;
import com.dsgrilo.coffeebreak.stok.repository.ProductRepository;
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


    private final ProductRepository productRepository;


    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductModel> createProduct(@RequestBody @Valid ProductModel productModel){

        return ResponseEntity.ok().body(productRepository.save(productModel));

    }


    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductModel>> findAllProducts(){

            List<ProductModel> products = productRepository.findAll();
            List<ComponentModel> components;

            return  ResponseEntity.ok().body(products);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity findById(@PathVariable("name") @Valid String name)
    {
        if(name.isEmpty()){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Obrigatorio colocar o nome do produto");
        }

        ProductModel productModel = productRepository.findByName(name);

        if(productModel == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Produto " + name + " não encontrado");
        }

        return ResponseEntity.ok().body(productModel);
    }

    /*
    @PostMapping("/image/{name}")
    public ResponseEntity<HttpStatus> uploadImage(@RequestBody MultipartFile image, @PathVariable("name") String name) throws IOException {


            ProductModel product = productRepository.findByName(name);

            product.setImage(image.getBytes());

            if(product.getImage() == null)
            {
                return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
            }


            return ResponseEntity.ok(HttpStatus.ACCEPTED);


    }
    */


}
