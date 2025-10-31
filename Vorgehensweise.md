# Vorgehensweise bei der Umsetzung der REST-API Aufgabe

## 1️. Verständnis der Aufgabenstellung
- REST-Interface in Java implementieren
- Datenquelle: CSV-Datei (nicht verändern)
- REST-Endpunkte:
    - **GET /persons** → alle Personen
    - **GET /persons/{id}** → einzelne Person
    - **GET /persons/color/{color}** → Personen mit bestimmter Farbe
    - **POST /persons** → neue Person hinzufügen

- Dependency Injection, Unit-Tests, Austauschbarkeit der Datenquelle

## 2️. Projektstruktur
- **Model**: Person (Datenmodell mit Lombok)
- **Repository**: CsvPersonRepository (liest CSV-Datei)
- **Service**: enthält Geschäftslogik
- **Controller**: PersonController (stellt REST-Endpunkte bereit)
- **Util**: ParserUtils (Hilfsmethoden für CSV-Parsing)

## 3️. CSV-Parsing
- Erkennung unregelmäßiger Formate (3 Fälle)
- Utility-Methoden in ParserUtils zusammengefasst
- Automatische Farbzuordnung über eine COLOR_MAP

## 4.️ Verwendung von Lombok
- @Data, @AllArgsConstructor, @NoArgsConstructor
- Automatische Generierung von Getter/Setter/Konstruktor
- Kürzerer, wartbarer Code

## 5️. Repository-Schicht
- Interface PersonRepository definiert
- CsvPersonRepository implementiert konkrete CSV-Lade-Logik
- Personen werden in einer internen Liste verwaltet
- Neue Personen können über die Methode save(Person p) hinzugefügt werden
- Austauschbarkeit der Datenquelle gewährleistet

## 6.️ Dependency Injection
- PersonService erhält PersonRepository über Konstruktor
- Der PersonService ist per Dependency Injection mit dem Repository verbunden und ermöglicht so einen austauschbaren Datenzugriff ohne direkte Kopplung an die Datenquelle.

## 7.️ REST-Controller
- Endpunkte mit @RestController, @GetMapping und @PostMapping
- Rückgabe von ResponseEntity<List<Person>>
- Einheitliche HTTP-Antwortcodes

## 8️. Unit-Tests
- Mockito zur Simulation des Services
- Tests für alle Endpunkte

## 9️. Logging
- SLF4J Logger statt printStackTrace()
- Logmeldungen bei erfolgreichem Laden der CSV-Datei und bei Problemen

## 10.Dokumentation
- README.md: Projektbeschreibung & API-Endpunkte
- Vorgehensweise.md: Detaillierte Entwicklungsdokumentation