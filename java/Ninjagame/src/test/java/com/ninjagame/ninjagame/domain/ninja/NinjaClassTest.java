package com.ninjagame.ninjagame.domain.ninja;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NinjaClassTest {

    @Test
    void testConvertAllowedNinjaClasses_ValidInput() {
        List<NinjaClass> result = NinjaClass.convertAllowedNinjaClasses("ASSASSIN,MARKSMAN");
        assertEquals(2, result.size());
        assertTrue(result.contains(NinjaClass.ASSASSIN));
        assertTrue(result.contains(NinjaClass.MARKSMAN));
    }

    @Test
    void testConvertAllowedNinjaClasses_IgnoresWhitespace() {
        List<NinjaClass> result = NinjaClass.convertAllowedNinjaClasses("  assassin , marksman  ");
        assertEquals(2, result.size());
        assertTrue(result.contains(NinjaClass.ASSASSIN));
        assertTrue(result.contains(NinjaClass.MARKSMAN));
    }

    @Test
    void testConvertAllowedNinjaClasses_EmptyInput() {
        List<NinjaClass> result = NinjaClass.convertAllowedNinjaClasses("");
        assertTrue(result.isEmpty());
    }

    @Test
    void testConvertAllowedNinjaClasses_NullInput() {
        List<NinjaClass> result = NinjaClass.convertAllowedNinjaClasses(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testConvertAllowedNinjaClasses_InvalidClassIgnored() {
        List<NinjaClass> result = NinjaClass.convertAllowedNinjaClasses("ASSASSIN,INVALIDCLASS");
        assertEquals(1, result.size());
        assertTrue(result.contains(NinjaClass.ASSASSIN));
    }

    @Test
    void testConvertToString() {
        List<NinjaClass> ninjaClasses = List.of(NinjaClass.ASSASSIN, NinjaClass.SAMURAI);
        String result = NinjaClass.convertToString(ninjaClasses);
        assertEquals("ASSASSIN,SAMURAI", result);
    }

    @Test
    void testIsValidClass_Valid() {
        assertTrue(NinjaClass.isValidClass("ASSASSIN"));
        assertTrue(NinjaClass.isValidClass("MARKSMAN"));
    }

    @Test
    void testIsValidClass_Invalid() {
        assertFalse(NinjaClass.isValidClass("INVALID_CLASS"));
        assertFalse(NinjaClass.isValidClass(" "));
        assertFalse(NinjaClass.isValidClass(""));
    }

    @Test
    void testToPrettyString() {
        assertEquals("Assassin", NinjaClass.ASSASSIN.toPrettyString());
        assertEquals("Marksman", NinjaClass.MARKSMAN.toPrettyString());
        assertEquals("Samurai", NinjaClass.SAMURAI.toPrettyString());
    }
}
