package de.vo.sample.service;

import de.vo.sample.model.Person;
import de.vo.sample.repository.CsvPersonRepository;
import de.vo.sample.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class PersonServiceTest {

    private PersonService personService;
    private PersonRepository personRepository;

    @Before
    public void setUp() {
        // Repository direkt instanziieren (CSV wird aus Ressourcen gelesen)
        personRepository = new CsvPersonRepository();

        // @PostConstruct wird in Unit-Tests nicht automatisch ausgeführt,
        // daher manuell aufrufen
        ReflectionTestUtils.invokeMethod(personRepository, "loadData");

        // Service bekommt Repository über den Konstruktor
        personService = new PersonService(personRepository);
    }

    @Test
    public void testLoadData_AllPersonsLoaded() {
        List<Person> persons = personService.getAllPersons();
        assertEquals("Es sollten genau 11 Datensätze geladen werden.", 11, persons.size());
    }

    @Test
    public void testGetPersonById_Found() {
        Optional<Person> personOpt = personService.getPersonById(1);

        assertTrue("Person mit ID 1 sollte existieren.", personOpt.isPresent());
        Person person = personOpt.get();
        assertEquals("Hans", person.getName());
        assertEquals("Müller", person.getLastname());
    }

    @Test
    public void testGetPersonById_NotFound() {
        Optional<Person> personOpt = personService.getPersonById(999);
        assertFalse("Person mit ID 999 sollte nicht existieren.", personOpt.isPresent());
    }

    @Test
    public void testGetPersonsByColor_Found() {
        List<Person> bluePersons = personService.getPersonsByColor("blau");

        assertFalse("Es sollte mindestens eine Person mit Farbe 'blau' geben.", bluePersons.isEmpty());
        assertEquals("blau", bluePersons.get(0).getColor());
    }

    @Test
    public void testGetPersonsByColor_NotFound() {
        List<Person> nonexistentColor = personService.getPersonsByColor("weiß");

        assertTrue("Für 'weiß' sollte keine Person existieren.", nonexistentColor.isEmpty());
    }
}
