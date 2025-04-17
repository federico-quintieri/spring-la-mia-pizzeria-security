package org.java.pizzeria.spring_la_mia_pizzeria_crud.controller;

import org.java.pizzeria.spring_la_mia_pizzeria_crud.model.Offerta;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.repository.OffertaRepository;
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
@RequestMapping("/offerte")
public class OffertaController {

    @Autowired
    private OffertaRepository repository;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("offerta") Offerta formOfferta, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "offerte/create-or-edit";
        }

        repository.save(formOfferta);

        return "redirect:/pizze/" + formOfferta.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("offerta", repository.findById(id).get());
        model.addAttribute("edit", true);
        return "offerte/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("offerta") Offerta newOfferta, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "offerte/create-or-edit";
        }
        repository.save(newOfferta);
        return "redirect:/pizze/" + newOfferta.getPizza().getId();
    }

    // Endpoint delete offerta
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        // Trovo offerta in base ad id
        Offerta offertaToDelete = repository.findById(id).get();
        // Cancello l'offerta selezionata in base ad id
        repository.delete(offertaToDelete);

        // Redirect alla pagina show della pizza relativa all'offerta cancellata
        return "redirect:/pizze/" + offertaToDelete.getPizza().getId();
    }
}
