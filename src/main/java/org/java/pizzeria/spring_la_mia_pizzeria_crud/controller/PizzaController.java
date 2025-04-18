package org.java.pizzeria.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.java.pizzeria.spring_la_mia_pizzeria_crud.model.Ingrediente;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.model.Offerta;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.model.Pizza;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.repository.IngredienteRepository;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.repository.OffertaRepository;
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

    // Mi serve perchè le row di offerte devo cancellare alla cancellazione di un
    // libro
    @Autowired
    private OffertaRepository offertaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

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
        model.addAttribute("ingredienti", ingredienteRepository.findAll());
        model.addAttribute("edit", false); // Aggiungo attributo edit come falso

        return "pizze/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        // Se le validazioni non sono andate a buon fine torna alla pagina del form
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredienti", ingredienteRepository.findAll());
            model.addAttribute("edit", false);
            return "pizze/create-or-edit";
        }

        // Converti ID -> Oggetti Ingredienti
        List<Ingrediente> ingredientiCompleti = formPizza.getIngredienti()
                .stream()
                .map(i -> ingredienteRepository.findById(i.getId()).orElse(null))
                .filter(i -> i != null)
                .toList();

        formPizza.setIngredienti(ingredientiCompleti);

        // Salvo la nuova pizza
        repository.save(formPizza);

        return "redirect:/pizze";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        // Mi ritorna la pagina con un form riempito dai valori presi da una pizza dal
        // db
        model.addAttribute("pizza", repository.findById(id).get());
        model.addAttribute("ingredienti", ingredienteRepository.findAll());
        model.addAttribute("edit", true);

        return "pizze/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        // Se le validazioni non sono andate a buon fine torna alla pagina del form
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredienti", ingredienteRepository.findAll());
            model.addAttribute("edit", true);
            return "pizze/create-or-edit";
        }

        List<Ingrediente> ingredientiCompleti = formPizza.getIngredienti()
                .stream()
                .map(i -> ingredienteRepository.findById(i.getId()).orElse(null))
                .filter(i -> i != null)
                .toList();

        formPizza.setIngredienti(ingredientiCompleti);

        // Aggiorna la pizza
        repository.save(formPizza);

        return "redirect:/pizze";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        // Prendo la pizza in base all'id
        Pizza pizza = repository.findById(id).get();

        // Ciclo tutte le offerte di UNA pizza e le cancello una per una
        for (Offerta offertaToDelete : pizza.getOfferte()) {
            offertaRepository.delete(offertaToDelete);
        }
        // Poi cancello la pizza
        repository.delete(pizza);
        return "redirect:/pizze";
    }

    @GetMapping("/{id}/offerta")
    public String offerta(@PathVariable Integer id, Model model) {
        Offerta offerta = new Offerta();
        offerta.setPizza(repository.findById(id).get());
        model.addAttribute("offerta", offerta);
        return "offerte/create-or-edit";
    }

}
