package com.example.demo.services;

import java.util.List;
import com.example.demo.models.Person;

public interface IPersonService {
    List<Person> listar();
    Person buscarPorId(Long id);
    Person buscarPorNombre(String name);
}

