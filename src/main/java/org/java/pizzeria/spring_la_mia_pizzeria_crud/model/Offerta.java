package org.java.pizzeria.spring_la_mia_pizzeria_crud.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "offerte")
public class Offerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Pizza da cui dipendo per fare la relazione 1 to n
    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable = false)
    private Pizza pizza;

    @NotNull(message = "Data inizio dell'offerta")
    @FutureOrPresent(message = "La data inizio offerta non è nel passato")
    private LocalDate dataInizioOfferta;

    @NotNull(message = "Data fine dell'offerta")
    @FutureOrPresent(message = "La data fine offerta non è nel passato")
    private LocalDate dataFineOfferta;

    @NotNull(message = "Nome offerta")
    private String NomeOfferta;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pizza getPizza() {
        return this.pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public LocalDate getDataInizioOfferta() {
        return this.dataInizioOfferta;
    }

    public void setDataInizioOfferta(LocalDate dataInizioOfferta) {
        this.dataInizioOfferta = dataInizioOfferta;
    }

    public LocalDate getDataFineOfferta() {
        return this.dataFineOfferta;
    }

    public void setDataFineOfferta(LocalDate dataFineOfferta) {
        this.dataFineOfferta = dataFineOfferta;
    }

    public String getNomeOfferta() {
        return this.NomeOfferta;
    }

    public void setNomeOfferta(String NomeOfferta) {
        this.NomeOfferta = NomeOfferta;
    }

}
