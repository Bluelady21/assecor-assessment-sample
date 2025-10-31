# Assecor Assessment Sample – Personen REST API

Dieses Projekt implementiert eine **REST-API in Java (Spring Boot)** zur Verwaltung von Personen und deren Lieblingsfarben.  
Die Daten werden **aus einer CSV-Datei** eingelesen und intern als Objekte gespeichert.

---

## Architekturüberblick

```
┌────────────────────────────┐
│ PersonController (REST)    │  ← REST-Endpunkte
└────────────┬───────────────┘
             │
┌────────────▼───────────────┐
│ PersonService (Business)   │  ← Geschäftslogik, DI via Konstruktor
└────────────┬───────────────┘
             │
┌────────────▼───────────────┐
│ CsvPersonRepository        │  ← Liest Daten aus CSV-Datei
│ implements PersonRepository│
└────────────────────────────┘
```

---

## Klassenübersicht

| Paket        | Klasse                                    | Aufgabe |
|--------------|-------------------------------------------|----------|
| `model`      | `Person`, `Place`                         | Datenmodell |
| `repository` | `PersonRepository`, `CsvPersonRepository` | Datenzugriff |
| `service`    | `PersonService`                           | Geschäftslogik, Dependency Injection |
| `controller` | `PersonController`                        | REST-Endpunkte |
| `util`       | `ParserUtils`                             | CSV-Parsing-Hilfsmethoden |
| `test`       | `PersonControllerTest`                    | Unit-Tests (Mockito + JUnit) |
|              | `PersonIntegrationTest`                   | Integrationstest: prüft REST-API mit echten CSV-Daten |

---

## CSV-Datei (`sample-input.csv`)

**Beispieldaten:**
```csv
Müller, Hans, 67742 Lauterecken, 1
Petersen, Peter, 18439 Stralsund, 2
```

Die Datei `sample-input.csv` wird beim Start automatisch geladen.  
Sie enthält unterschiedliche Formatvarianten, die vom Parser korrekt erkannt werden.

---

### Farbzuordnung

| ID | Farbe   |
|----|----------|
| 1  | blau     |
| 2  | grün     |
| 3  | violett  |
| 4  | rot      |
| 5  | gelb     |
| 6  | türkis   |
| 7  | weiß     |

---

## REST-Endpunkte

| Endpunkt                 | Methode | Beschreibung |
|--------------------------|----------|---------------|
| `/persons`               | **GET**  | Gibt alle Personen zurück |
| `/persons/{id}`          | **GET**  | Gibt eine Person anhand der ID zurück |
| `/persons/color/{color}` | **GET**  | Gibt alle Personen mit der angegebenen Lieblingsfarbe zurück |
| `/persons`               | **POST** | Fügt eine neue Person hinzu |

---

## Technischer Stack

- **Java 17+**
- **Spring Boot 3**
- **Lombok** (`@Data`, `@AllArgsConstructor`, etc.)
- **JUnit 4** & **Mockito** – für Unit Tests
- **SLF4J / Logback** – Logging
- **Maven** – Build Tool

---

## Tests

- **Unit-Tests:** Prüfen REST-Endpunkte und Geschäftslogik mit Mockito
- **Integrationstests:** Stellen sicher, dass die komplette REST-Schnittstelle korrekt mit den echten CSV-Daten funktioniert

---
