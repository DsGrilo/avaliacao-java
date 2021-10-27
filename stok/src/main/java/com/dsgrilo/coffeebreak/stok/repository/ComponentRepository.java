package com.dsgrilo.coffeebreak.stok.repository;

import com.dsgrilo.coffeebreak.stok.model.ComponentModel;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ComponentRepository extends JpaRepository<ComponentModel, UUID> {



}
