package com.ninjagame.ninjagame.domain.player;

import com.ninjagame.ninjagame.domain.Rarity;
import com.ninjagame.ninjagame.domain.ninja.NinjaClass;
import com.ninjagame.ninjagame.domain.weapon.Weapon;
import com.ninjagame.ninjagame.domain.weapon.WeaponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerNinjaTest {

    private PlayerNinja playerNinja;
    private Weapon weapon;

    @BeforeEach
    void setUp() {
        // Create a weapon for the ninja
        weapon = new Weapon("Shadow Dagger", WeaponType.TANTO, 50.0, Rarity.RARE, null);

        // Create a PlayerNinja instance
        playerNinja = new PlayerNinja("Stealth Master", NinjaClass.ASSASSIN, 100, 150, 50, 100, Rarity.EPIC, weapon);
    }

    @Test
    void testPlayerNinjaInitialization() {
        assertEquals("Stealth Master", playerNinja.getName());
        assertEquals(NinjaClass.ASSASSIN, playerNinja.getNinjaClass());
        assertEquals(100, playerNinja.getStrength());
        assertEquals(150, playerNinja.getHealth());
        assertEquals(50, playerNinja.getArmor());
        assertEquals(100, playerNinja.getEnergy());
        assertEquals(Rarity.EPIC, playerNinja.getRarity());
        assertEquals(weapon, playerNinja.getWeaponEquipped());
    }

    @Test
    void testChangeWeapon() {
        Weapon newWeapon = new Weapon("Silent Katana", WeaponType.KATANA, 80.0, Rarity.LEGENDARY, null);
        playerNinja.setWeaponEquipped(newWeapon);

        assertEquals(newWeapon, playerNinja.getWeaponEquipped());
        assertEquals("Silent Katana", playerNinja.getWeaponEquipped().getName());
    }

    @Test
    void testSetNullWeapon() {
        playerNinja.setWeaponEquipped(null);
        assertNull(playerNinja.getWeaponEquipped(), "Weapon should be null after unequipping.");
    }
}
