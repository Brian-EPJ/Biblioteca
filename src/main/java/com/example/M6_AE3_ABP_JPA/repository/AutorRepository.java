package com.example.M6_AE3_ABP_JPA.repository;

import com.example.M6_AE3_ABP_JPA.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Indica que esta interfaz es un componente de acceso a datos (DAO o repositorio)
// que gestionará operaciones con la entidad 'Autor'.
// La anotación @Repository permite que Spring la detecte automáticamente
// como un bean de persistencia y maneje posibles excepciones de base de datos.
//
// Al extender 'JpaRepository<Autor, Long>', esta interfaz hereda un conjunto completo
// de métodos CRUD (crear, leer, actualizar y eliminar) para trabajar con la entidad Autor.
//
// El primer parámetro (Autor) es la clase de la entidad a gestionar.
// El segundo parámetro (Long) indica el tipo de dato de su clave primaria (id).
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    //Metodo para buscar un nombre en la tabla Autor
    Optional<Autor> findByNombreIgnoreCase(String nombre);
}
