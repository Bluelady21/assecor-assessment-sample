package de.vo.sample.controller;

import de.vo.sample.model.Person;
import de.vo.sample.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // GET /persons
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

    // GET /persons/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable int id) {
        return personService.getPersonById(id)
                .map(ResponseEntity::ok)                      // wenn Person vorhanden → 200 OK
                .orElse(ResponseEntity.notFound().build());   // sonst → 404 Not Found
    }

    // GET /persons/color/{color}
    @GetMapping("/color/{color}")
    public ResponseEntity<List<Person>> getPersonsByColor(@PathVariable String color) {
        List<Person> persons = personService.getPersonsByColor(color);
        return persons.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(persons);
    }

    // POST /persons
    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        Person savedPerson = personService.addPerson(person);
        if (savedPerson == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }
}
