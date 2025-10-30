package de.vo.sample.util;

import lombok.Value;
import java.util.Map;

public final class ParserUtils {

    private ParserUtils() {}

    /**
     * Zerlegt eine Ortsangabe in PLZ und Stadt.
     * @param placePart
     * @return
     */
    public static Place parsePlace(String placePart) {
        if (placePart == null || placePart.isBlank()) return new Place("", "");

        placePart = placePart.trim();

        if (placePart.matches("^\\d{5}\\b.*")) {
            String[] parts = placePart.split("\\s+", 2);
            String zipcode = parts[0];
            String city = parts.length > 1 ? parts[1].trim() : "";
            return new Place(zipcode, city);
        }

        // Fallback: kein PLZ-Muster, nur Stadtname oder fehlerhafte Daten
        return new Place("", placePart);
    }

    /**
     * Versucht, eine ganze Zahl aus einem String zu parsen.
     * Gibt -1 zurück, wenn der Wert keine gültige Zahl ist.
     */
    public static int tryParseInt(String value) {
        if (value == null || value.isBlank()) return -1;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Wandelt eine Farb-ID (z. B. "1") in eine Farb-Beschreibung um.
     * Gibt "unbekannt" zurück, falls die ID ungültig ist oder nicht gefunden wird.
     */
    public static String parseColor(String value, Map<Integer, String> colorMap) {
        int colorId = tryParseInt(value);
        return colorMap.getOrDefault(colorId, "unbekannt");
    }

    /**
     * Repräsentiert eine Ortsangabe mit PLZ und Stadt.
     */
    @Value
    public static class Place {
        String zipcode;
        String city;
    }
}
