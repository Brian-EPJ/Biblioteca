package com.example.M6_AE3_ABP_JPA.controller;

import com.example.M6_AE3_ABP_JPA.model.Autor;
import com.example.M6_AE3_ABP_JPA.model.Libro;
import com.example.M6_AE3_ABP_JPA.service.AutorService;
import com.example.M6_AE3_ABP_JPA.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/biblioteca")
public class BibliotecaController {

    private final AutorService autorService;
    private final LibroService libroService;

    public BibliotecaController(AutorService autorService, LibroService libroService) {
        this.autorService = autorService;
        this.libroService = libroService;
    }

    // ===============================
    // 🧩 LISTA PRINCIPAL
    // ===============================
    @GetMapping
    public String lista(Model model) {
        List<Autor> autores = autorService.obtenerTodos();
        List<Libro> libros = libroService.obtenerTodos();

        model.addAttribute("autores", autores);
        model.addAttribute("libros", libros);
        return "biblioteca/lista";
    }

    // ===============================
    // 🧑‍💼 AUTORES
    // ===============================

    @GetMapping("/autores/nuevo")
    public String nuevoAutor(Model model) {
        model.addAttribute("titulo", "Nuevo Autor");
        model.addAttribute("autor", new Autor());
        return "biblioteca/autor-form";
    }

    @PostMapping("/autores/guardar")
    public String guardarAutor(@ModelAttribute Autor autor) {
        autorService.guardarAutor(autor);
        return "redirect:/biblioteca";
    }

    @GetMapping("/autores/editar/{id}")
    public String editarAutor(@PathVariable Long id, Model model) {
        Autor autor = autorService.buscarPorId(id);
        model.addAttribute("titulo", "Editar Autor");
        model.addAttribute("autor", autor);
        return "biblioteca/autor-form";
    }

    @PostMapping("/autores/actualizar")
    public String actualizarAutor(@RequestParam Long id, @RequestParam String nombre) {
        autorService.actualizarNombre(id, nombre);
        return "redirect:/biblioteca";
    }

    // ===============================
    // 📘 LIBROS
    // ===============================

    @GetMapping("/libros/nuevo")
    public String nuevoLibro(Model model) {
        model.addAttribute("titulo", "Nuevo Libro");
        model.addAttribute("libro", new Libro());
        model.addAttribute("autores", autorService.obtenerTodos());
        return "biblioteca/libro-form";
    }

    @PostMapping("/libros/guardar")
    public String guardarLibro(@RequestParam Long idAutor, @ModelAttribute Libro libro) {
        libroService.guardarLibro(idAutor, libro);
        return "redirect:/biblioteca";
    }

    @GetMapping("/libros/eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibro(id);
        return "redirect:/biblioteca";
    }
}
