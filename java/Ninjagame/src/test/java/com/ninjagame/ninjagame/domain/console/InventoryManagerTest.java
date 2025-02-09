package com.ninjagame.ninjagame.domain.console;

import com.ninjagame.ninjagame.domain.player.Inventory;
import com.ninjagame.ninjagame.domain.player.InventoryManager;
import com.ninjagame.ninjagame.domain.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest {
    private Player player;
    private MockInputProvider mockInputProvider;
    private InventoryManager inventoryManager;

    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer", 1, 0, new Inventory());
        mockInputProvider = new MockInputProvider();
        inventoryManager = new InventoryManager(player, mockInputProvider);
    }

    @Test
    void testViewEmptyInventory() {
        mockInputProvider.addInput(1); // Selecting "View Weapons"
        mockInputProvider.addInput(6); // Exit

        inventoryManager.showMenu();
        assertTrue(player.getInventory().getWeapons().isEmpty(), "Weapons list should be empty initially.");
    }

    @Test
    void testRemoveNonExistentWeapon() {
        mockInputProvider.addInput(4); // Select "Remove weapon"
        mockInputProvider.addInput(999); // Enter invalid weapon ID
        mockInputProvider.addInput(6); // Exit

        inventoryManager.showMenu();
        assertTrue(player.getInventory().getWeapons().isEmpty(), "No weapons should be removed.");
    }
}
