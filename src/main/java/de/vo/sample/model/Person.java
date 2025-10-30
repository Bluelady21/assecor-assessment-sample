package de.vo.sample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                 // Getter, Setter, toString, equals, hashCode
@NoArgsConstructor    // Leerer Konstruktor
@AllArgsConstructor   // Konstruktor mit allen Feldern
public class Person {
    private int id;
    private String name;
    private String lastname;
    private String zipcode;
    private String city;
    private String color;
}
