package org.java.pizzeria.spring_la_mia_pizzeria_crud.service;

import java.util.Optional;

import org.java.pizzeria.spring_la_mia_pizzeria_crud.model.Offerta;
import org.java.pizzeria.spring_la_mia_pizzeria_crud.repository.OffertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OffertaService {

    @Autowired
    private OffertaRepository offertaRepository;

    public Optional<Offerta> findByIdOfferta(Integer id) {
        return offertaRepository.findById(id);
    }

    public Offerta create(Offerta offerta) {
        return offertaRepository.save(offerta);
    }

    public Offerta update(Offerta offerta) {
        return offertaRepository.save(offerta);
    }

    public void deleteById(Integer id) {
        Offerta offertaToDelete = offertaRepository.findById(id).get();
        offertaRepository.delete(offertaToDelete);
    }

    public void delete(Offerta offerta) {
        offertaRepository.delete(offerta);
    }
}
