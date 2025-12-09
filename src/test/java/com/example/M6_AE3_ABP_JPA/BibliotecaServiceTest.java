package com.example.M6_AE3_ABP_JPA;

import com.example.M6_AE3_ABP_JPA.model.Autor;
import com.example.M6_AE3_ABP_JPA.model.Libro;
import com.example.M6_AE3_ABP_JPA.repository.AutorRepository;
import com.example.M6_AE3_ABP_JPA.repository.LibroRepository;
import com.example.M6_AE3_ABP_JPA.service.BibliotecaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class BibliotecaServiceTest {

    @Autowired
    private AutorRepository autorRepo;

    @Autowired
    private LibroRepository libroRepo;

    @Autowired
    private BibliotecaService bibliotecaService;

    @Test
    void registrarAutorYLibro_CuandoFallaElLibro_NoDebeGuardarNada() {
        Autor autor = new Autor();
        autor.setNombre("Autor Prueba");

        Libro libro = new Libro();
        libro.setTitulo(null); // <-- esto debe provocar fallo

        try {
            bibliotecaService.registrarAutorYLibro(autor, libro);
            Assertions.fail("Debería lanzar excepción");
        } catch (IllegalArgumentException ex) {
            // se espera
        }

        // verificar que NO se guardó nada
        boolean autorExiste = autorRepo.findByNombreIgnoreCase("Autor Prueba").isPresent();
        Assertions.assertFalse(autorExiste, "El autor no debe haberse guardado por rollback");
    }
}