package com.ninjagame.ninjagame.domain.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory(500, 50);
    }

    @Test
    void testInventoryInitialization() {
        assertEquals(500, inventory.getCoins());
        assertEquals(50, inventory.getGems());
        assertNull(inventory.getWeapons(), "Weapons list should be null initially");
        assertNull(inventory.getNinjas(), "Ninjas list should be null initially");
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
    void testManageWeapons() {
        List<PlayerWeapon> weapons = new ArrayList<>();
        weapons.add(new PlayerWeapon());  // Assuming PlayerWeapon has a no-arg constructor
        inventory.setWeapons(weapons);
        assertNotNull(inventory.getWeapons());
        assertEquals(1, inventory.getWeapons().size());
    }

    @Test
    void testManageNinjas() {
        List<PlayerNinja> ninjas = new ArrayList<>();
        ninjas.add(new PlayerNinja());  // Assuming PlayerNinja has a no-arg constructor
        inventory.setNinjas(ninjas);
        assertNotNull(inventory.getNinjas());
        assertEquals(1, inventory.getNinjas().size());
    }

    @Test
    void testSetNullWeapons() {
        inventory.setWeapons(null);
        assertNull(inventory.getWeapons(), "Weapons list should be null when set to null");
    }

    @Test
    void testSetNullNinjas() {
        inventory.setNinjas(null);
        assertNull(inventory.getNinjas(), "Ninjas list should be null when set to null");
    }
}
