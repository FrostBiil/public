package com.ninjagame.ninjagame.domain.player;

import com.ninjagame.ninjagame.domain.Rarity;
import com.ninjagame.ninjagame.domain.weapon.Weapon;
import com.ninjagame.ninjagame.domain.weapon.WeaponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerWeaponTest {

    private PlayerWeapon playerWeapon;
    private Weapon baseWeapon;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        // Create a base weapon
        baseWeapon = new Weapon("Shadow Blade", WeaponType.KATANA, 75.5, Rarity.EPIC, null);
        
        // Create an inventory (dummy inventory, no weapons needed)
        inventory = new Inventory(1000, 50);
        
        // Initialize PlayerWeapon
        playerWeapon = new PlayerWeapon(baseWeapon, inventory);
    }

    @Test
    void testPlayerWeaponInitialization() {
        assertEquals("Shadow Blade", playerWeapon.getName());
        assertEquals(WeaponType.KATANA, playerWeapon.getType());
        assertEquals(75.5, playerWeapon.getAttackPower());
        assertEquals(Rarity.EPIC, playerWeapon.getRarity());
        assertEquals(1, playerWeapon.getLevel());
        assertEquals(inventory, playerWeapon.getInventory());
        assertEquals(baseWeapon, playerWeapon.getBaseWeapon());
    }

    @Test
    void testLevelUpIncreasesStats() {
        double initialAttackPower = playerWeapon.getAttackPower();
        
        playerWeapon.levelUp();
        
        assertEquals(2, playerWeapon.getLevel());
        assertEquals(initialAttackPower * 1.15, playerWeapon.getAttackPower(), 0.001); // Allow small rounding errors
    }

    @Test
    void testMultipleLevelUps() {
        double initialAttackPower = playerWeapon.getAttackPower();
        
        playerWeapon.levelUp();
        playerWeapon.levelUp();
        playerWeapon.levelUp();
        
        assertEquals(4, playerWeapon.getLevel());
        double expectedAttackPower = initialAttackPower * Math.pow(1.15, 3);
        assertEquals(expectedAttackPower, playerWeapon.getAttackPower(), 0.001);
    }

    @Test
    void testSetBaseWeapon() {
        Weapon newWeapon = new Weapon("Dagger of Shadows", WeaponType.TANTO, 50.0, Rarity.RARE, null);
        playerWeapon.setBaseWeapon(newWeapon);
        
        assertEquals("Dagger of Shadows", playerWeapon.getBaseWeapon().getName());
        assertEquals(WeaponType.TANTO, playerWeapon.getBaseWeapon().getType());
        assertEquals(50.0, playerWeapon.getBaseWeapon().getDamage());
    }

    @Test
    void testSetInventory() {
        Inventory newInventory = new Inventory(500, 10);
        playerWeapon.setInventory(newInventory);
        
        assertEquals(newInventory, playerWeapon.getInventory());
    }
}
