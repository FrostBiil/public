package com.ninjagame.ninjagame.domain.combat;

import com.ninjagame.ninjagame.domain.ninja.Ninja;
import com.ninjagame.ninjagame.domain.ninja.NinjaClass;
import com.ninjagame.ninjagame.domain.player.Player;
import com.ninjagame.ninjagame.domain.weapon.Weapon;
import com.ninjagame.ninjagame.domain.weapon.WeaponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerCombatTest {

    private PlayerCombat playerCombat;
    private EnemyCombat enemyCombat;
    private Ninja attacker1, attacker2, attacker3;
    private Ninja defender1, defender2, defender3;

    @BeforeEach
    void setUp() {
        // Create player and enemy player
        Player player = new Player("Player1", 10, 500, null);
        Player enemy = new Player("EnemyAI", 10, 500, null);

        // Create attacking ninjas
        attacker1 = new Ninja("Shadow Assassin", NinjaClass.ASSASSIN, 80, 100, 20, 100, null, 
                              new Weapon("Dagger", WeaponType.TANTO, 30, null, null));
        attacker2 = new Ninja("Silent Marksman", NinjaClass.MARKSMAN, 70, 90, 15, 90, null, 
                              new Weapon("Bow", WeaponType.LONGBOW, 40, null, null));
        attacker3 = new Ninja("Fierce Samurai", NinjaClass.SAMURAI, 100, 110, 25, 110, null, 
                              new Weapon("Katana", WeaponType.KATANA, 50, null, null));

        // Initialize player team
        Ninja[] playerNinjas = {attacker1, attacker2, attacker3};
        playerCombat = new PlayerCombat(player, playerNinjas);

        // Create defending ninjas
        defender1 = new Ninja("Rival Assassin", NinjaClass.ASSASSIN, 75, 100, 20, 100, null, null);
        defender2 = new Ninja("Rival Marksman", NinjaClass.MARKSMAN, 65, 85, 15, 90, null, null);
        defender3 = new Ninja("Rival Samurai", NinjaClass.SAMURAI, 95, 105, 25, 110, null, null);

        // Initialize enemy team
        Ninja[] enemyNinjas = {defender1, defender2, defender3};
        enemyCombat = new EnemyCombat(enemy, enemyNinjas);
    }

    @Test
    void testChooseAttackingNinja_Valid() {
        Ninja selectedNinja = playerCombat.chooseAttackingNinja("Silent Marksman");
        assertEquals(attacker2, selectedNinja);
    }

    @Test
    void testChooseAttackingNinja_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> playerCombat.chooseAttackingNinja("Nonexistent Ninja"));
    }

    @Test
    void testChooseAttackingNinja_DeadNinja() {
        attacker1.setHealth(0);
        assertThrows(IllegalArgumentException.class, () -> playerCombat.chooseAttackingNinja("Shadow Assassin"));
    }

    @Test
    void testChooseEnemyTarget_Valid() {
        Ninja selectedTarget = playerCombat.chooseEnemyTarget(enemyCombat, "Rival Samurai");
        assertEquals(defender3, selectedTarget);
    }

    @Test
    void testChooseEnemyTarget_Invalid() {
        assertThrows(IllegalArgumentException.class, () -> playerCombat.chooseEnemyTarget(enemyCombat, "Unknown Ninja"));
    }

    @Test
    void testChooseEnemyTarget_DeadNinja() {
        defender2.setHealth(0);
        assertThrows(IllegalArgumentException.class, () -> playerCombat.chooseEnemyTarget(enemyCombat, "Rival Marksman"));
    }

    @Test
    void testHasAliveNinjas_WhenAllAlive() {
        assertTrue(playerCombat.hasAliveNinjas());
    }

    @Test
    void testHasAliveNinjas_WhenSomeAlive() {
        attacker1.setHealth(0);
        attacker2.setHealth(0);
        assertTrue(playerCombat.hasAliveNinjas());
    }

    @Test
    void testHasAliveNinjas_WhenNoneAlive() {
        attacker1.setHealth(0);
        attacker2.setHealth(0);
        attacker3.setHealth(0);
        assertFalse(playerCombat.hasAliveNinjas());
    }
}
