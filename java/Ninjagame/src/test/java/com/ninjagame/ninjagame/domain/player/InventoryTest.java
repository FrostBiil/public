package com.ninjagame.ninjagame.domain.player;

import com.ninjagame.ninjagame.domain.ninja.PlayerNinja;
import com.ninjagame.ninjagame.domain.weapon.PlayerWeapon;
import com.ninjagame.ninjagame.domain.ninja.NinjaClass;
import com.ninjagame.ninjagame.domain.weapon.WeaponType;
import com.ninjagame.ninjagame.domain.weapon.Weapon;
import com.ninjagame.ninjagame.domain.Rarity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory(500, 50);
    }

    @Test
    void testInventoryInitialization() {
        assertEquals(500, inventory.getCoins(), "Initial coin balance should be 500.");
        assertEquals(50, inventory.getGems(), "Initial gem balance should be 50.");

        assertNotNull(inventory.getWeapons(), "Weapons list should be initialized.");
        assertTrue(inventory.getWeapons().isEmpty(), "Weapons list should be empty initially.");

        assertNotNull(inventory.getNinjas(), "Ninjas list should be initialized.");
        assertTrue(inventory.getNinjas().isEmpty(), "Ninjas list should be empty initially.");
    }

    @Test
    void testUpdateCoins() {
        inventory.setCoins(1000);
        assertEquals(1000, inventory.getCoins());
    }

    @Test
    void testUpdateGems() {
        inventory.setGems(200);
        assertEquals(200, inventory.getGems());
    }

    @Test
    void testAddWeaponToInventory() {
        Weapon baseWeapon = new Weapon("Kunai", WeaponType.KUNAI, 35, Rarity.UNCOMMON, Collections.singletonList("Assassin"));
        PlayerWeapon playerWeapon = new PlayerWeapon(1, baseWeapon, inventory);

        inventory.addWeapon(playerWeapon);

        assertNotNull(inventory.getWeapons());
        assertEquals(1, inventory.getWeapons().size());
        assertEquals(playerWeapon, inventory.getWeapons().get(0));
    }

    @Test
    void testAddNinjaToInventory() {
        PlayerNinja ninja = new PlayerNinja(1, "Shadow", NinjaClass.ASSASSIN, 50, 200, 30, 100, Rarity.EPIC, null);

        inventory.addNinja(ninja);

        assertNotNull(inventory.getNinjas());
        assertEquals(1, inventory.getNinjas().size());
        assertEquals(ninja, inventory.getNinjas().get(0));
    }

    @Test
    void testRemoveWeapon() {
        Weapon baseWeapon = new Weapon("Shuriken", WeaponType.SHURIKEN, 40, Rarity.RARE, Collections.singletonList("Assassin"));
        PlayerWeapon playerWeapon = new PlayerWeapon(2, baseWeapon, inventory);

        inventory.addWeapon(playerWeapon);
        assertEquals(1, inventory.getWeapons().size());

        inventory.getWeapons().remove(playerWeapon);
        assertEquals(0, inventory.getWeapons().size());
    }

    @Test
    void testRemoveNinja() {
        PlayerNinja ninja = new PlayerNinja(2, "Storm", NinjaClass.SAMURAI, 60, 250, 40, 90, Rarity.LEGENDARY, null);

        inventory.addNinja(ninja);
        assertEquals(1, inventory.getNinjas().size());

        inventory.getNinjas().remove(ninja);
        assertEquals(0, inventory.getNinjas().size());
    }

    @Test
    void testSetNullWeaponsShouldResetToEmptyList() {
        inventory.setWeapons(null);
        assertNotNull(inventory.getWeapons(), "Weapons list should be initialized even when set to null.");
        assertTrue(inventory.getWeapons().isEmpty(), "Weapons list should reset to empty instead of null.");
    }

    @Test
    void testSetNullNinjasShouldResetToEmptyList() {
        inventory.setNinjas(null);
        assertNotNull(inventory.getNinjas(), "Ninjas list should be initialized even when set to null.");
        assertTrue(inventory.getNinjas().isEmpty(), "Ninjas list should reset to empty instead of null.");
    }
}
