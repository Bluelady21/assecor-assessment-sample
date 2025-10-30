package de.vo.sample.repository;

import de.vo.sample.model.Person;
import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    List<Person> findAll();
    List<Person> findByColor(String color);
    Optional<Person> findById(int id);

    //neue Person hinzufügen
    Person save(Person person);
}
