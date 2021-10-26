package com.dsgrilo.coffeebreak.stok.controller;


import com.dsgrilo.coffeebreak.stok.model.ComponentModel;
import com.dsgrilo.coffeebreak.stok.repository.ComponentRepository;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/component")
public class ComponentController {

    private ComponentRepository componentRepository;

    public ComponentController(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @GetMapping
    public ResponseEntity<List<ComponentModel>> findAll(){

        return ResponseEntity.ok().body(componentRepository.findAll());
    }

}
