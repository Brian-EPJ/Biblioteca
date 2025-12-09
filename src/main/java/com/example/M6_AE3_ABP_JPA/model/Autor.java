package com.example.M6_AE3_ABP_JPA.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Autor {

    // Indica que este campo será la clave primaria (PRIMARY KEY) de la tabla
    // y que su valor se generará automáticamente en la base de datos (autoincremental)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // Relación uno a muchos: un autor puede tener varios libros asociados.
    // 'mappedBy = "autor"' indica que esta relación está mapeada en la clase Libro,
    // específicamente en el atributo 'autor', que contiene la clave foránea (id_autor) en la base de datos.
    // 'cascade = CascadeType.ALL' significa que las operaciones realizadas sobre el autor
    // (guardar, actualizar, eliminar, etc.) se aplicarán automáticamente a sus libros relacionados.
    // 'fetch = FetchType.LAZY' indica que la lista de libros no se cargará desde la base de datos
    // de inmediato al obtener el autor, sino solo cuando se acceda a ella (carga diferida),
    // lo cual mejora el rendimiento y evita consultas innecesarias.
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Libro> libros;
}
