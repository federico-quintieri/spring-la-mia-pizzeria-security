package org.java.pizzeria.spring_la_mia_pizzeria_crud.controller;

import org.java.pizzeria.spring_la_mia_pizzeria_crud.model.Ingrediente;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.model.Pizza;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.repository.IngredienteRepository;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredienti")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    // Index per lista di ingredienti
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("ingredienti", ingredienteService.findAllIngredients());
        return "ingredienti/index";
    }

    // Get per la show
    @GetMapping("/dettagli/{id}")
    public String show(@PathVariable Integer id, Model model) {

        model.addAttribute("ingrediente", ingredienteService.findByIdIngredient(id).get());

        return "/ingredienti/show";

    }

    // Una get per arrivare a form creazione ingrediente
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingrediente", new Ingrediente());
        model.addAttribute("edit", false);
        return "ingredienti/create-or-edit";
    }

    // Una post per inviare form creazione ingrediente
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingrediente") Ingrediente newIngrediente, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/ingredienti/create-or-edit";
        }

        // Salvo nuovo ingrediente (row) se non ci sono errori di compilazione del form
        ingredienteService.createIngredient(newIngrediente);

        // Reindirizza ad URL ingredienti non view
        return "redirect:/ingredienti";
    }

    // Una get per editare form modifica ingrediente
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ingrediente", ingredienteService.findByIdIngredient(id).get());

        model.addAttribute("edit", true);

        return "ingredienti/create-or-edit";
    }

    // Una post per inviare form modifica ingrediente
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("ingrediente") Ingrediente newIngrediente,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "ingredienti/create-or-edit";
        }

        ingredienteService.updateIngredient(newIngrediente);

        return "redirect:/ingredienti";
    }

    // Una post per inviare delete ingrediente
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // Prima di cancellare un ingrediente, devo togliere questi ingrediente da tutti
        // le pizze che ce l'hanno

        Ingrediente ingredienteDaCancellare = ingredienteService.findByIdIngredient(id).get();

        for (Pizza linkedPizza : ingredienteDaCancellare.getPizze()) {
            linkedPizza.getIngredienti().remove(ingredienteDaCancellare);
        }

        ingredienteService.deleteByIdIngredient(id);
        return "redirect:/ingredienti";
    }
}
