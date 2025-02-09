package com.ninjagame.ninjagame.domain.ninja;

import com.ninjagame.ninjagame.domain.Rarity;
import com.ninjagame.ninjagame.domain.weapon.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NinjaTest {

    private Ninja ninja;
    private Weapon weapon;
    private NinjaClass ninjaClass;

    @BeforeEach
    void setUp() {
        weapon = new Weapon();
        ninjaClass = NinjaClass.ASSASSIN;

        ninja = new Ninja("Shadow", ninjaClass, 50.0, 100.0, 25.0, 75.0, Rarity.EPIC, weapon);
    }

    @Test
    void testNinjaInitialization() {
        assertEquals("Shadow", ninja.getName());
        assertEquals(ninjaClass, ninja.getNinjaClass());
        assertEquals(50.0, ninja.getStrength());
        assertEquals(100.0, ninja.getHealth());
        assertEquals(25.0, ninja.getArmor());
        assertEquals(75.0, ninja.getEnergy());
        assertEquals(Rarity.EPIC, ninja.getRarity());
        assertEquals(weapon, ninja.getWeaponEquipped());
    }

    @Test
    void testUpdateStrength() {
        ninja.setStrength(60.0);
        assertEquals(60.0, ninja.getStrength());
    }

    @Test
    void testUpdateHealth() {
        ninja.setHealth(120.0);
        assertEquals(120.0, ninja.getHealth());
    }

    @Test
    void testUpdateArmor() {
        ninja.setArmor(30.0);
        assertEquals(30.0, ninja.getArmor());
    }

    @Test
    void testUpdateEnergy() {
        ninja.setEnergy(50.0);
        assertEquals(50.0, ninja.getEnergy());
    }

    @Test
    void testChangeRarity() {
        ninja.setRarity(Rarity.LEGENDARY);
        assertEquals(Rarity.LEGENDARY, ninja.getRarity());
    }

    @Test
    void testChangeWeapon() {
        Weapon newWeapon = new Weapon();
        ninja.setWeaponEquipped(newWeapon);
        assertEquals(newWeapon, ninja.getWeaponEquipped());
    }

    @Test
    void testSetNullWeapon() {
        ninja.setWeaponEquipped(null);
        assertNull(ninja.getWeaponEquipped(), "Weapon should be null when unequipped");
    }

    @Test
    void testSetNullRarity() {
        ninja.setRarity(null);
        assertNull(ninja.getRarity(), "Rarity should be null when reset");
    }

    @Test
    void testGetWeaponDamage_NoWeapon() {
        ninja.setWeaponEquipped(null); // Unequip weapon
        assertEquals(0.0, ninja.getWeaponDamage(), "Weapon damage should be 0 if no weapon is equipped");
    }

    @Test
    void testGetWeaponDamage_WithWeapon() {
        Weapon testWeapon = new Weapon();
        testWeapon.setDamage(40.0);
        ninja.setWeaponEquipped(testWeapon);
        assertEquals(40.0, ninja.getWeaponDamage(), "Weapon damage should match equipped weapon's damage");
    }
}
