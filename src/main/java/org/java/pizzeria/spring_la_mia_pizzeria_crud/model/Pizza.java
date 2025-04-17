package org.java.pizzeria.spring_la_mia_pizzeria_crud.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pizze")
public class Pizza {

    // Genero colonna id auto incrementale
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Genero colonna nome
    @NotBlank
    private String nome;

    // Genero colonna descrizione
    @NotBlank(message = "Inserisci descrizione")
    @Size(min = 8, max = 500, message = "Descrizione deve essere di almeno 8 caratteri")
    private String descrizione;

    // Genero colonna foto(url)
    @NotBlank
    @Size(min = 10, max = 100, message = "Url deve essere di almeno 10 caratteri")
    private String imageurl;

    // Genero colonna prezzo BigDecimal
    @Min(value = 0, message = "Prezzo minimo parte da zero")
    private BigDecimal prezzo;

    // Definisco relazione con 1 to n con Offerta
    @OneToMany(mappedBy = "pizza")
    private List<Offerta> offerte;

    // Getter e setter
    public List<Offerta> getOfferte() {
        return this.offerte;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getImageurl() {
        return this.imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public BigDecimal getPrezzo() {
        return this.prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return String.format("Nome pizza : %s \n Prezzo: %d ", this.nome, this.prezzo);
    }

}
