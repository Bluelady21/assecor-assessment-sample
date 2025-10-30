package de.vo.sample.service;

import de.vo.sample.model.Person;
import de.vo.sample.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    // Dependency Injection des Repositories
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(int id) {
        return personRepository.findById(id);
    }

    public List<Person> getPersonsByColor(String color) {
        return personRepository.findByColor(color);
    }

    public Person addPerson(Person person) {
        return personRepository.save(person);
    }
}
