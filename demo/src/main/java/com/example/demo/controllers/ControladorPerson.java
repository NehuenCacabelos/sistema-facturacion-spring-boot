package com.example.demo.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.Person;
import com.example.demo.services.IPersonService;

@RestController
@RequestMapping("/api/persons")
public class ControladorPerson {

    private final IPersonService servicio;

    public ControladorPerson(IPersonService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/listar")
    public List<Person> listar() {
        return servicio.listar();
    }

    @GetMapping("/buscar/{id}")
    public Person buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id);
    }

    @GetMapping("/buscar-nombre/{name}")
    public Person buscarPorNombre(@PathVariable String name) {
        return servicio.buscarPorNombre(name);
    }
}

