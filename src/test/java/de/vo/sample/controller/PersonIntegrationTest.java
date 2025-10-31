package de.vo.sample.controller;

import de.vo.sample.AssessmentApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssessmentApplication.class)
@AutoConfigureMockMvc
public class PersonIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Testet GET /persons
     * Erwartet: 11 Personen aus der CSV-Datei
     */
    @Test
    public void testGetAllPersons_ReturnsExpectedData() throws Exception {
        mockMvc.perform(get("/persons").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(11))
                // Erste Person
                .andExpect(jsonPath("$[0].lastname").value("Müller"))
                .andExpect(jsonPath("$[0].name").value("Hans"))
                .andExpect(jsonPath("$[0].zipcode").value("67742"))
                .andExpect(jsonPath("$[0].city").value("Lauterecken"))
                .andExpect(jsonPath("$[0].color").value("blau"))
                // Letzte Person
                .andExpect(jsonPath("$[10].lastname").value("Klaussen"))
                .andExpect(jsonPath("$[10].name").value("Klaus"))
                .andExpect(jsonPath("$[10].city").value("Hierach"))
                .andExpect(jsonPath("$[10].color").value("grün"));
    }

    // Enthält Sonderzeichen in "city"
    @Test
    public void testPersonMitSonderzeichen() throws Exception {
        mockMvc.perform(get("/persons/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Anders"))
                .andExpect(jsonPath("$.lastname").value("Andersson"))
                .andExpect(jsonPath("$.zipcode").value("32132"))
                .andExpect(jsonPath("$.city", containsString("Schweden")))
                .andExpect(jsonPath("$.color").value("grün"));
    }

    // Fehlende Werte – sollte korrekt geladen, aber teilweise leer sein
    @Test
    public void testPersonMitLeerenFeldern() throws Exception {
        mockMvc.perform(get("/persons/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bertram"))
                .andExpect(jsonPath("$.lastname").value("Bart"))
                .andExpect(jsonPath("$.zipcode", anyOf(isEmptyOrNullString(), equalTo(""))))
                .andExpect(jsonPath("$.city", anyOf(isEmptyOrNullString(), equalTo(""))))
                .andExpect(jsonPath("$.color", anyOf(isEmptyOrNullString(), equalTo("unbekannt"))));
    }

    // Kein Name, aber PLZ und Ort vorhanden
    @Test
    public void testPersonOhneName() throws Exception {
        mockMvc.perform(get("/persons/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zipcode").value("12313"))
                .andExpect(jsonPath("$.city").value("Wasweißich"))
                .andExpect(jsonPath("$.color").value("blau"));
    }
}
