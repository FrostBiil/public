package com.ninjagame.ninjagame.domain.weapon;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeaponTypeTest {

    @Test
    void testConvertAllowedWeaponTypes_ValidInput() {
        List<WeaponType> result = WeaponType.convertAllowedWeaponTypes("KATANA,TANTO");
        assertEquals(2, result.size());
        assertTrue(result.contains(WeaponType.KATANA));
        assertTrue(result.contains(WeaponType.TANTO));
    }

    @Test
    void testConvertAllowedWeaponTypes_IgnoresWhitespace() {
        List<WeaponType> result = WeaponType.convertAllowedWeaponTypes("  katana , tanto  ");
        assertEquals(2, result.size());
        assertTrue(result.contains(WeaponType.KATANA));
        assertTrue(result.contains(WeaponType.TANTO));
    }

    @Test
    void testConvertAllowedWeaponTypes_EmptyInput() {
        List<WeaponType> result = WeaponType.convertAllowedWeaponTypes("");
        assertTrue(result.isEmpty());
    }

    @Test
    void testConvertAllowedWeaponTypes_NullInput() {
        List<WeaponType> result = WeaponType.convertAllowedWeaponTypes(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testConvertAllowedWeaponTypes_InvalidWeaponIgnored() {
        List<WeaponType> result = WeaponType.convertAllowedWeaponTypes("KATANA,INVALID_WEAPON");
        assertEquals(1, result.size());
        assertTrue(result.contains(WeaponType.KATANA));
    }

    @Test
    void testConvertAllowedWeaponType_ValidInput() {
        WeaponType result = WeaponType.convertAllowedWeaponType("KATANA");
        assertEquals(WeaponType.KATANA, result);
    }

    @Test
    void testConvertAllowedWeaponType_IgnoresWhitespace() {
        WeaponType result = WeaponType.convertAllowedWeaponType("   katana   ");
        assertEquals(WeaponType.KATANA, result);
    }

    @Test
    void testConvertAllowedWeaponType_InvalidWeapon() {
        WeaponType result = WeaponType.convertAllowedWeaponType("INVALID_WEAPON");
        assertNull(result);
    }

    @Test
    void testConvertAllowedWeaponType_NullInput() {
        WeaponType result = WeaponType.convertAllowedWeaponType(null);
        assertNull(result);
    }

    @Test
    void testConvertToString_List() {
        List<WeaponType> weapons = List.of(WeaponType.KATANA, WeaponType.SHURIKEN);
        String result = WeaponType.convertListToString(weapons);
        assertEquals("KATANA,SHURIKEN", result);
    }

    @Test
    void testConvertToString_SingleWeapon() {
        String result = WeaponType.convertToString(WeaponType.KATANA);
        assertEquals("KATANA", result);
    }

    @Test
    void testConvertToString_NullWeapon() {
        String result = WeaponType.convertToString(null);
        assertEquals("", result);
    }

    @Test
    void testIsValidWeapon_Valid() {
        assertTrue(WeaponType.isValidWeapon("KATANA"));
        assertTrue(WeaponType.isValidWeapon("SHURIKEN"));
    }

    @Test
    void testIsValidWeapon_Invalid() {
        assertFalse(WeaponType.isValidWeapon("INVALID_WEAPON"));
        assertFalse(WeaponType.isValidWeapon(" "));
        assertFalse(WeaponType.isValidWeapon(""));
    }

    @Test
    void testToPrettyString() {
        assertEquals("Katana", WeaponType.KATANA.toPrettyString());
        assertEquals("Shuriken", WeaponType.SHURIKEN.toPrettyString());
        assertEquals("Tanto", WeaponType.TANTO.toPrettyString());
    }
}
