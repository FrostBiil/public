package com.ninjagame.ninjagame.domain.player;

import com.ninjagame.ninjagame.domain.ninja.PlayerNinja;
import com.ninjagame.ninjagame.domain.ninja.NinjaClass;
import com.ninjagame.ninjagame.domain.weapon.PlayerWeapon;
import com.ninjagame.ninjagame.domain.weapon.Weapon;
import com.ninjagame.ninjagame.domain.Rarity;
import com.ninjagame.ninjagame.domain.weapon.WeaponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("NinjaMaster", 5, 0, new Inventory(1000, 100));
    }

    @Test
    void testPlayerInitialization() {
        assertEquals("NinjaMaster", player.getName());
        assertEquals(5, player.getLevel());
        assertEquals(0, player.getExp());
        assertNotNull(player.getInventory());
        assertEquals(1000, player.getInventory().getCoins());
        assertEquals(100, player.getInventory().getGems());
    }

    @Test
    void testUpdateLevel() {
        player.setLevel(10);
        assertEquals(10, player.getLevel());
    }

    @Test
    void testEarnExpAndLevelUp() {
        player.addExperience(100);
        assertEquals(6, player.getLevel());
        assertEquals(0, player.getExp());

        player.addExperience(200);
        assertEquals(8, player.getLevel());
        assertEquals(0, player.getExp());
    }

    @Test
    void testAddCoins() {
        player.addCoins(500);
        assertEquals(1500, player.getInventory().getCoins());
    }

    @Test
    void testInventoryAlwaysInitialized() {
        player.setInventory(null);
        assertNotNull(player.getInventory(), "Inventory should never be null.");
    }

    @Test
    void testAddWeaponToInventory() {
        Weapon baseWeapon = new Weapon("Katana", com.ninjagame.ninjagame.domain.weapon.WeaponType.KATANA, 50, Rarity.RARE, Collections.singletonList("Samurai"));
        PlayerWeapon playerWeapon = new PlayerWeapon(baseWeapon, player.getInventory());

        player.getInventory().addWeapon(playerWeapon);
        assertFalse(player.getInventory().getWeapons().isEmpty());
        assertEquals("Katana", player.getInventory().getWeapons().get(0).getName());
    }

    @Test
    void testRemoveWeaponFromInventory() {
        Weapon baseWeapon = new Weapon("Katana", com.ninjagame.ninjagame.domain.weapon.WeaponType.KATANA, 50, Rarity.RARE, Collections.singletonList("Samurai"));
        PlayerWeapon playerWeapon = new PlayerWeapon(baseWeapon, player.getInventory());

        player.getInventory().addWeapon(playerWeapon);
        player.removeWeapon(playerWeapon.getId());

        assertTrue(player.getInventory().getWeapons().isEmpty());
    }

    @Test
    void testAddNinjaToInventory() {
        PlayerNinja ninja = new PlayerNinja("Shadow", NinjaClass.ASSASSIN, 50, 200, 30, 100, Rarity.EPIC, null);
        player.getInventory().addNinja(ninja);

        assertFalse(player.getInventory().getNinjas().isEmpty());
        assertEquals("Shadow", player.getInventory().getNinjas().get(0).getName());
    }

    @Test
    void testRemoveNinjaFromInventory() {
        PlayerNinja ninja = new PlayerNinja("Shadow", NinjaClass.ASSASSIN, 50, 200, 30, 100, Rarity.EPIC, null);
        player.getInventory().addNinja(ninja);
        player.removeNinja(ninja.getId());

        assertTrue(player.getInventory().getNinjas().isEmpty());
    }

    @Test
    void testEquipWeaponToNinja() {
        PlayerNinja ninja = new PlayerNinja(1, "Shadow", NinjaClass.ASSASSIN, 50, 200, 30, 100, Rarity.EPIC, null);
        player.getInventory().addNinja(ninja);

        Weapon baseWeapon = new Weapon("Kunai", WeaponType.KUNAI, 35, Rarity.UNCOMMON, Collections.singletonList("Assassin"));
        PlayerWeapon playerWeapon = new PlayerWeapon(1, baseWeapon, player.getInventory());

        player.getInventory().addWeapon(playerWeapon);
        player.equipWeaponToNinja(ninja.getId(), playerWeapon.getId());

        assertEquals(playerWeapon, ninja.getWeaponEquipped(), "Weapon should be equipped to ninja.");
    }

}
