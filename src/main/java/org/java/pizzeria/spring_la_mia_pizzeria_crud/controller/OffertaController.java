package org.java.pizzeria.spring_la_mia_pizzeria_crud.controller;

import org.java.pizzeria.spring_la_mia_pizzeria_crud.model.Offerta;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.repository.OffertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
            return "offerte/create";
        }

        repository.save(formOfferta);

        return "redirect:/pizze";
    }

}
