package com.example.M6_AE3_ABP_JPA.service;

import com.example.M6_AE3_ABP_JPA.model.Autor;
import com.example.M6_AE3_ABP_JPA.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository repo;

    public AutorService(AutorRepository repo) {
        this.repo = repo;
    }


    public Autor guardarAutor(Autor autor) {
        Optional<Autor> existente = repo.findByNombreIgnoreCase(autor.getNombre());

        // Si existe un autor con ese nombre y NO es el mismo autor que se está editando
        if (existente.isPresent() && !existente.get().getId().equals(autor.getId())) {
            throw new RuntimeException("Ya existe un autor con ese nombre");
        }

        return repo.save(autor);
    }

    public Autor actualizarNombre(Long idAutor, String nuevoNombre) {

        // 1️⃣ Buscar el autor en la base de datos por su ID.
        //    Si no existe, findById() devolverá un Optional vacío.
        Optional<Autor> autorOpt = repo.findById(idAutor);

        // 2️⃣ Validar si el autor existe.
        if (autorOpt.isEmpty()) {
            // Si no se encuentra, lanzamos una excepción para detener la operación.
            throw new RuntimeException("No existe un autor con el ID: " + idAutor);
        }

        // 3️⃣ Obtener el autor encontrado desde el Optional.
        Autor autor = autorOpt.get();

        // 4️⃣ Validar si ya existe otro autor con el mismo nombre (opcional, evita duplicados).
        Optional<Autor> autorDuplicado = repo.findByNombreIgnoreCase(nuevoNombre);
        if (autorDuplicado.isPresent() && !autorDuplicado.get().getId().equals(idAutor)) {
            throw new RuntimeException("Ya existe otro autor con ese nombre");
        }

        // 5️⃣ Asignar el nuevo nombre al autor.
        autor.setNombre(nuevoNombre);

        // 6️⃣ Guardar los cambios en la base de datos.
        //    Como el autor ya tiene un ID, JPA interpreta esto como una actualización (UPDATE).
        return repo.save(autor);
    }
    public Autor buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe un autor con el ID: " + id));
    }

    public List<Autor> obtenerTodos() {
        return repo.findAll();
    }

}
