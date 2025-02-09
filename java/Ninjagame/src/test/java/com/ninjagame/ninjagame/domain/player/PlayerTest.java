package com.ninjagame.ninjagame.domain.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("NinjaMaster", 5, 200, new Inventory(1000, 100));
    }

    @Test
    void testPlayerInitialization() {
        assertEquals("NinjaMaster", player.getName());
        assertEquals(5, player.getLevel());
        assertEquals(200, player.getExp());
        assertNotNull(player.getInventory());
    }

    @Test
    void testUpdateLevel() {
        player.setLevel(10);
        assertEquals(10, player.getLevel());
    }

    @Test
    void testEarnExp() {
        player.setExp(player.getExp() + 100);
        assertEquals(300, player.getExp());
    }

    @Test
    void testNullInventory() {
        player.setInventory(null);
        assertNull(player.getInventory());
    }
}
