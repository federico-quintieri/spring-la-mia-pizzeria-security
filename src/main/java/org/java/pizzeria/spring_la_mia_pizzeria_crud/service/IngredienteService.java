package org.java.pizzeria.spring_la_mia_pizzeria_crud.service;

import java.util.List;
import java.util.Optional;

import org.java.pizzeria.spring_la_mia_pizzeria_crud.model.Ingrediente;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.model.Pizza;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public List<Ingrediente> findAllIngredients() {
        return ingredienteRepository.findAll();
    }

    public Optional<Ingrediente> findByIdIngredient(Integer id) {
        return ingredienteRepository.findById(id);
    }

    public Ingrediente createIngredient(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public Ingrediente updateIngredient(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public void deleteByIdIngredient(Integer id) {

        Ingrediente ingredienteDaCancellare = ingredienteRepository.findById(id).get();

        // Per cancellare un ingrediente devo cancellarlo anche da tutte le pizze che lo
        // contengono
        for (Pizza linkedPizza : ingredienteDaCancellare.getPizze()) {
            linkedPizza.getIngredienti().remove(ingredienteDaCancellare);
        }

        ingredienteRepository.deleteById(id);
    }


}
