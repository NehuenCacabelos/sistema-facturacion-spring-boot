package com.example.demo.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.models.Person;

@Repository
public interface IPersonRepository extends CrudRepository<Person, Long> {

    // Query method por convención de nombre
    Person findByName(String name);

    // Consulta personalizada JPQL
    @Query("SELECT p FROM Person p WHERE p.id > :id AND p.name = :name")
    List<Person> buscarPorNombreyIdMayor(@Param("id") Long id, @Param("name") String name);
}

