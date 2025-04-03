package org.java.pizzeria.spring_la_mia_pizzeria_crud.repository;

import org.java.pizzeria.spring_la_mia_pizzeria_crud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository <Pizza,Integer>{
    
}
