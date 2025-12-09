package com.example.M6_AE3_ABP_JPA.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    // Relación muchos-a-uno: varios libros pueden tener el mismo autor
    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
