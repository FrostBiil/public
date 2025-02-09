package com.ninjagame.ninjagame.domain.weapon;

import com.ninjagame.ninjagame.domain.Rarity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WeaponTest {

    private Weapon weapon;
    private List<String> allowedClasses;

    @BeforeEach
    void setUp() {
        allowedClasses = new ArrayList<>(List.of("ASSASSIN", "MARKSMAN"));
        weapon = new Weapon("Shadow Blade", WeaponType.KATANA, 75.5, Rarity.EPIC, allowedClasses);
    }

    @Test
    void testWeaponInitialization() {
        assertEquals("Shadow Blade", weapon.getName());
        assertEquals(WeaponType.KATANA, weapon.getType());
        assertEquals(75.5, weapon.getDamage());
        assertEquals(Rarity.EPIC, weapon.getRarity());
        assertEquals(2, weapon.getAllowedNinjaClasses().size());
        assertTrue(weapon.getAllowedNinjaClasses().contains("ASSASSIN"));
    }


    @Test
    void testUpdateWeaponName() {
        weapon.setName("Dagger of Shadows");
        assertEquals("Dagger of Shadows", weapon.getName());
    }

    @Test
    void testUpdateDamage() {
        weapon.setDamage(90.0);
        assertEquals(90.0, weapon.getDamage());
    }

    @Test
    void testUpdateRarity() {
        weapon.setRarity(Rarity.LEGENDARY);
        assertEquals(Rarity.LEGENDARY, weapon.getRarity());
    }

    @Test
    void testChangeAllowedNinjaClasses() {
        List<String> newClasses = List.of("SAMURAI");
        weapon.setAllowedNinjaClasses(newClasses);
        assertEquals(1, weapon.getAllowedNinjaClasses().size());
        assertTrue(weapon.getAllowedNinjaClasses().contains("SAMURAI"));
    }


    @Test
    void testSetNullAllowedNinjaClasses() {
        weapon.setAllowedNinjaClasses(null);
        assertNull(weapon.getAllowedNinjaClasses(), "Allowed Ninja Classes should be null when set to null");
    }

    @Test
    void testEmptyAllowedNinjaClasses() {
        weapon.setAllowedNinjaClasses(new ArrayList<>());
        assertNotNull(weapon.getAllowedNinjaClasses());
        assertTrue(weapon.getAllowedNinjaClasses().isEmpty());
    }
}
