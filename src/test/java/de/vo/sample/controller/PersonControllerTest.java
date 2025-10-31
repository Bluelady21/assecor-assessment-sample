package de.vo.sample.controller;

import de.vo.sample.model.Person;
import de.vo.sample.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class PersonControllerTest {

    private PersonController controller;

    @Mock
    private PersonService service;

    private Person hans, peter, lena;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initialisiert @Mock-Felder
        controller = new PersonController(service);

        hans = new Person(1, "Hans", "Müller", "67742", "Lauterecken", "blau");
        peter = new Person(2, "Peter", "Petersen", "18439", "Stralsund", "grün");
        lena = new Person(3, "Lena", "Schmidt", "80331", "München", "rot");
    }

    // ---------- GET /persons ----------
    @Test
    public void testGetAllPersons_returnsList() {
        // Service simulieren
        when(service.getAllPersons()).thenReturn(List.of(hans, peter));

        ResponseEntity<List<Person>> response = controller.getAllPersons();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody()); // <- Sicherstellen, dass etwas zurückkommt
        assertEquals(2, response.getBody().size());
        assertEquals("Hans", response.getBody().get(0).getName());
    }

    @Test
    public void testGetAllPersons_emptyList() {
        when(service.getAllPersons()).thenReturn(List.of());

        ResponseEntity<List<Person>> response = controller.getAllPersons();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
    }

    // ---------- GET /persons/{id} ----------
    @Test
    public void testGetPersonById_Found() {
        when(service.getPersonById(1)).thenReturn(Optional.of(hans));

        ResponseEntity<Person> response = controller.getPersonById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Hans", response.getBody().getName());
    }

    @Test
    public void testGetPersonById_NotFound() {
        when(service.getPersonById(999)).thenReturn(Optional.empty());

        ResponseEntity<Person> response = controller.getPersonById(999);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    // ---------- GET /persons/color/{color} ----------
    @Test
    public void testGetPersonsByColor_Found() {
        when(service.getPersonsByColor("blau")).thenReturn(List.of(hans));

        ResponseEntity<List<Person>> response = controller.getPersonsByColor("blau");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("blau", response.getBody().get(0).getColor());
    }

    @Test
    public void testGetPersonsByColor_NotFound() {
        when(service.getPersonsByColor("weiß")).thenReturn(List.of());

        ResponseEntity<List<Person>> response = controller.getPersonsByColor("weiß");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    // ---------- POST /persons ----------
    @Test
    public void testAddPerson_Success() {
        when(service.addPerson(any(Person.class))).thenReturn(lena);

        ResponseEntity<Person> response = controller.addPerson(lena);

        assertEquals(201, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
        assertEquals("Lena", response.getBody().getName());
        assertEquals("München", response.getBody().getCity());
    }

    @Test
    public void testAddPerson_NullReturned() {
        when(service.addPerson(any(Person.class))).thenReturn(null);

        ResponseEntity<Person> response = controller.addPerson(lena);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
