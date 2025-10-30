package de.vo.sample.repository;

import de.vo.sample.model.Person;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static de.vo.sample.util.ParserUtils.*;

@Repository
public class CsvPersonRepository implements PersonRepository {

    private static final Logger log = LoggerFactory.getLogger(CsvPersonRepository.class);

    private static final Map<Integer, String> COLOR_MAP = Map.of(
            1, "blau",
            2, "grün",
            3, "violett",
            4, "rot",
            5, "gelb",
            6, "türkis",
            7, "weiß"
    );

    private final List<Person> persons = new ArrayList<>();

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(persons);
    }

    @Override
    public Optional<Person> findById(int id) {
        return persons.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    @Override
    public List<Person> findByColor(String color) {
        return persons.stream()
                .filter(p -> p.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

    @Override
    public Person save(Person person) {
//        if (person == null
//                || person.getName() == null
//                || person.getLastname() == null) {
//            return null;
//        }

        int newId = persons.size() + 1;
        person.setId(newId);
        persons.add(person);
        return person;
    }


    @PostConstruct
    public void loadData() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream("/sample-input.csv")),
                StandardCharsets.UTF_8))) {
            log.info("Lese Personen aus sample-input.csv ...");

            String line;
            int id = 0;

            while ((line = reader.readLine()) != null) {
                line = line.replace("\uFEFF", "").trim();
                if (line.isEmpty() || line.matches("^,+$")) continue;

                String[] parts = Arrays.stream(line.split(",", -1)) // Das -1 bewirkt, dass leere Felder am Ende erhalten bleiben
                        .map(String::trim)
                        .toArray(String[]::new);

                // FALL 1: Zeile beginnt mit 5-stelliger PLZ
                if (parts[0].matches("^\\d{5}\\b.*")) {
                    id++;
                    Place place = parsePlace(parts[0]);
                    String color = parseColor(parts.length > 1 ? parts[1] : null, COLOR_MAP);
                    persons.add(new Person(id, "", "", place.getZipcode(), place.getCity(), color));
                    continue;
                }

                // FALL 2: Nur Nachname + Vorname
                if (parts.length == 2 || (parts.length == 3 && parts[2].isEmpty())) {
                    id++;
                    persons.add(new Person(id, parts[1], parts[0], "", "", "unbekannt"));
                    continue;
                }

                // FALL 3: Vollständiger Datensatz (z. B. „Müller, Hans, 67742 Lauterecken, 1“)
                if (parts.length >= 3) {
                    id++;

                    String lastname = parts[0].trim();
                    String firstname = parts[1].trim();
                    String lastPart = parts[parts.length - 1].trim();

                    // Farbe ermitteln
                    String color = parseColor(lastPart, COLOR_MAP);

                    // Ort/PLZ ermitteln (je nach Farb-ID eine Spalte davor)
                    // wenn lastPart eine Zahl ist → Ort liegt eine Spalte davor, sonst direkt das letzte Feld
                    boolean hasColorId = tryParseInt(lastPart) != -1;
                    String placePart = parts[parts.length - (hasColorId ? 2 : 1)].trim();

                    Place place = parsePlace(placePart);

                    persons.add(new Person(id, firstname, lastname, place.getZipcode(), place.getCity(), color));
                }
            }

            log.info("{} Personen erfolgreich geladen.", persons.size());

        } catch (NullPointerException e) {
            log.error("CSV-Datei 'sample-input.csv' wurde nicht gefunden. Stelle sicher, dass sie im Ressourcenordner liegt.", e);
        } catch (Exception e) {
            log.error("Fehler beim Einlesen der CSV-Datei: {}", e.getMessage(), e);
        }
    }
}
