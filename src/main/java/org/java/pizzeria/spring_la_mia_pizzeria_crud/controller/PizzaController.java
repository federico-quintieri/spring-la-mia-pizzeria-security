package org.java.pizzeria.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.java.pizzeria.spring_la_mia_pizzeria_crud.model.Offerta;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.model.Pizza;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

    @Autowired
    private PizzaRepository repository;

    @GetMapping
    public String index(Model model) {
        List<Pizza> pizze = repository.findAll(); // Prendo le pizze dal DB
        model.addAttribute("pizze", pizze);
        return "pizze/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        // Trovo solo la row in base all'id e la metto nell'attributo pizza
        model.addAttribute("pizza", repository.findById(id).get());

        // Faccio una attributo al model e gli passo tutte le pizze per la creazione dei
        // pulsanti in base alla grandezza
        model.addAttribute("sizePizze", repository.findAll());

        return "pizze/show";
    }

    @GetMapping("/searchByNome")
    public String StringByTitle(@RequestParam(name = "nome") String nome, Model model) {

        List<Pizza> pizze = repository.findByNomeContaining(nome);
        model.addAttribute("pizze", pizze);

        return "pizze/index";
    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("pizza", new Pizza());

        return "pizze/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        // Se le validazioni non sono andate a buon fine torna alla pagina del form
        if (bindingResult.hasErrors()) {
            return "pizze/create";
        }

        // Salvo la nuova pizza
        repository.save(formPizza);

        return "redirect:/pizze";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        // Mi ritorna la pagina con un form riempito dai valori presi da una pizza dal
        // db
        model.addAttribute("pizza", repository.findById(id).get());

        return "pizze/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        // Se le validazioni non sono andate a buon fine torna alla pagina del form
        if (bindingResult.hasErrors()) {
            return "pizze/edit";
        }

        // Aggiorna la pizza
        repository.save(formPizza);

        return "redirect:/pizze";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        repository.deleteById(id);
        return "redirect:/pizze";
    }

    @GetMapping("/{id}/offerta")
    public String offerta(@PathVariable Integer id, Model model) {
        Offerta offerta = new Offerta();
        offerta.setPizza(repository.findById(id).get());
        model.addAttribute("offerta", offerta);
        return "offerte/create";
    }

}
