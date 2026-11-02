package com.example.demo.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.models.Person;
import com.example.demo.repositories.IPersonRepository;

@Service
public class PersonService implements IPersonService {

    private final IPersonRepository repositorio;

    public PersonService(IPersonRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Person> listar() {
        return (List<Person>) repositorio.findAll();
    }

    @Override
    public Person buscarPorId(Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    public Person buscarPorNombre(String name) {
        return repositorio.findByName(name);
    }
}

