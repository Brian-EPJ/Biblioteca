package com.example.M6_AE3_ABP_JPA.service;

import com.example.M6_AE3_ABP_JPA.model.Autor;
import com.example.M6_AE3_ABP_JPA.model.Libro;
import com.example.M6_AE3_ABP_JPA.repository.AutorRepository;
import com.example.M6_AE3_ABP_JPA.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final LibroRepository libroRepo;
    private final AutorRepository autorRepo;


    public LibroService(LibroRepository repoL, AutorRepository repoA) {
        this.libroRepo = repoL;
        this.autorRepo = repoA;
    }

    public Libro guardarLibro(Long idAutor, Libro libro) {

        // 1️⃣ Buscar el autor en la base de datos por su ID.
        //    Esto devuelve un Optional<Autor>, que puede contener un autor o estar vacío si no existe.
        Optional<Autor> autorOpt = autorRepo.findById(idAutor);

        // 2️⃣ Validar si el autor existe.
        //    Si el Optional está vacío (no se encontró el autor), lanzamos una excepción para evitar errores.
        if (autorOpt.isEmpty()) {
            // Lanzamos un error que interrumpe la ejecución del método.
            // Esto evita guardar un libro sin un autor válido.
            throw new RuntimeException("El autor no existe");
        }

        // 3️⃣ Obtener el autor encontrado.
        //    Ahora que sabemos que sí existe, extraemos el objeto Autor del Optional.
        Autor autor = autorOpt.get();

        // 4️⃣ Asociar el autor al libro.
        //    Aquí establecemos la relación ManyToOne:
        //    el libro “pertenece” a este autor.
        libro.setAutor(autor);

        // 5️⃣ Guardar el libro en la base de datos.
        //    Cuando se guarde, JPA almacenará automáticamente el id del autor
        //    en la columna "id_autor" (gracias a la anotación @JoinColumn en la entidad Libro).
        return libroRepo.save(libro);
    }

    public List<Libro> obtenerTodos(){
        return libroRepo.findAll();
    }

    public void eliminarLibro(Long idLibro) {

        // 1️⃣ Buscar si el libro existe antes de eliminarlo.
        //    Esto evita errores en caso de que el ID no esté en la base de datos.
        Optional<Libro> libroOpt = libroRepo.findById(idLibro);

        // 2️⃣ Si no existe, lanzamos un error.
        //    Así prevenimos intentar eliminar algo que no está en la base de datos.
        if (libroOpt.isEmpty()) {
            throw new RuntimeException("El libro con ID " + idLibro + " no existe");
        }

        // 3️⃣ Si existe, lo eliminamos usando su ID.
        //    deleteById() ejecuta un DELETE en la base de datos.
        libroRepo.deleteById(idLibro);

        // 💡 También podrías usar libroRepo.delete(libroOpt.get());
        //    pero deleteById() es más directo cuando solo necesitas el ID.

        // 4️⃣ No afecta al autor relacionado.
        //    Debido a que la relación es @ManyToOne sin cascada REMOVE,
        //    el autor NO se borra automáticamente.
    }



}
