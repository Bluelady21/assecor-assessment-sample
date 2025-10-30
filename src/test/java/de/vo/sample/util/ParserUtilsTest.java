package de.vo.sample.util;

import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

public class ParserUtilsTest {

    @Test
    public void testParsePlace() {
        assertEquals("12345", ParserUtils.parsePlace("12345 Berlin").getZipcode());
        assertEquals("Berlin", ParserUtils.parsePlace("12345 Berlin").getCity());
        assertEquals("Hamburg", ParserUtils.parsePlace("Hamburg").getCity());
        assertEquals("", ParserUtils.parsePlace(" ").getZipcode());
        assertEquals("", ParserUtils.parsePlace(null).getCity());
    }

    @Test
    public void testTryParseInt() {
        assertEquals(42, ParserUtils.tryParseInt("42"));
        assertEquals(-1, ParserUtils.tryParseInt("abc"));
    }

    @Test
    public void testParseColor() {
        Map<Integer,String> colors = Map.of(1,"Rot",2,"Blau");
        assertEquals("Rot", ParserUtils.parseColor("1", colors));
        assertEquals("unbekannt", ParserUtils.parseColor("99", colors));
    }
}
