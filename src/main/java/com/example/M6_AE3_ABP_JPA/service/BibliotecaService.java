package com.example.M6_AE3_ABP_JPA.service;

import com.example.M6_AE3_ABP_JPA.model.Autor;
import com.example.M6_AE3_ABP_JPA.model.Libro;
import com.example.M6_AE3_ABP_JPA.repository.AutorRepository;
import com.example.M6_AE3_ABP_JPA.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BibliotecaService {

    private final AutorRepository autorRepo;
    private final LibroRepository libroRepo;

    public BibliotecaService(AutorRepository autorRepo, LibroRepository libroRepo) {
        this.autorRepo = autorRepo;
        this.libroRepo = libroRepo;
    }

    /**
     * Guarda un autor y su libro en una misma transacción.
     * Si el libro no tiene título o el autor ya existe, lanza excepción
     * y hace rollback automáticamente.
     */
    @Transactional
    public void registrarAutorYLibro(Autor autor, Libro libro) {

        // Validaciones básicas
        if (autor.getNombre() == null || autor.getNombre().isBlank()) {
            throw new IllegalArgumentException("El autor debe tener un nombre");
        }

        if (libro.getTitulo() == null || libro.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El libro debe tener un título");
        }

        // Evita duplicados de autores
        if (autorRepo.findByNombreIgnoreCase(autor.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un autor con ese nombre");
        }

        // Guarda primero el autor
        Autor autorGuardado = autorRepo.save(autor);

        // Asocia y guarda el libro
        libro.setAutor(autorGuardado);
        libroRepo.save(libro);
    }
}
