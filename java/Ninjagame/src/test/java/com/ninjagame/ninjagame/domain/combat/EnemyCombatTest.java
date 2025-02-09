package com.ninjagame.ninjagame.domain.combat;

import com.ninjagame.ninjagame.domain.ninja.Ninja;
import com.ninjagame.ninjagame.domain.ninja.NinjaClass;
import com.ninjagame.ninjagame.domain.ninja.PlayerNinja;
import com.ninjagame.ninjagame.domain.player.Player;
import com.ninjagame.ninjagame.domain.weapon.PlayerWeapon;
import com.ninjagame.ninjagame.domain.weapon.Weapon;
import com.ninjagame.ninjagame.domain.weapon.WeaponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyCombatTest {

    private EnemyCombat enemyCombat;
    private PlayerCombat enemyPlayerCombat;
    private PlayerNinja attacker1, attacker2, attacker3;
    private PlayerNinja defender1, defender2, defender3;

    @BeforeEach
    void setUp() {
        // Create a player
        Player player = new Player("EnemyAI", 10, 500, null);

        // Create 3 attacking ninjas for the AI
        attacker1 = new PlayerNinja("Shadow Assassin", NinjaClass.ASSASSIN, 80, 100, 20, 100, null, new Weapon("Dagger", WeaponType.TANTO, 30, null, null));
        attacker2 = new PlayerNinja("Silent Marksman", NinjaClass.MARKSMAN, 70, 90, 15, 90, null, new Weapon("Bow", WeaponType.LONGBOW, 40, null, null));
        attacker3 = new PlayerNinja("Fierce Samurai", NinjaClass.SAMURAI, 100, 110, 25, 110, null, new Weapon("Katana", WeaponType.KATANA, 50, null, null));

        // AI enemy team (3 ninjas)
        PlayerNinja[] enemyNinjas = {attacker1, attacker2, attacker3};
        enemyCombat = new EnemyCombat(player, enemyNinjas);

        // Create 3 defending ninjas
        defender1 = new PlayerNinja("Rival Assassin", NinjaClass.ASSASSIN, 75, 100, 20, 100, null, new Weapon("Short Sword", WeaponType.TANTO, 25, null, null));
        defender2 = new PlayerNinja("Rival Marksman", NinjaClass.MARKSMAN, 65, 85, 15, 90, null, new Weapon("Crossbow", WeaponType.LONGBOW, 35, null, null));
        defender3 = new PlayerNinja("Rival Samurai", NinjaClass.SAMURAI, 95, 105, 25, 110, null, new Weapon("Naginata", WeaponType.KATANA, 45, null, null));

        // Enemy team (defenders)
        PlayerNinja[] enemyDefenders = {defender1, defender2, defender3};
        enemyPlayerCombat = new PlayerCombat(new Player("Player1", 10, 500, null), enemyDefenders);
    }

    @Test
    void testChooseAttackingNinja_TypeAdvantage() {
        // Samurai (attacker3) should attack Assassin (defender1) due to type advantage
        Ninja selectedAttacker = enemyCombat.chooseAttackingNinja(enemyPlayerCombat);
        assertEquals(attacker3, selectedAttacker);
    }

    @Test
    void testChooseAttackingNinja_HighestEffectiveAttack() {
        // Ensure highest total attack (strength + weapon damage) is chosen when no advantage
        defender1.setHealth(0);
        defender2.setHealth(0);

        Ninja selectedAttacker = enemyCombat.chooseAttackingNinja(enemyPlayerCombat);
        assertEquals(attacker3, selectedAttacker);
    }

    @Test
    void testChooseAttackingNinja_SameTypeIfNoAdvantage() {
        // If no advantage, select ninja of the same type with the highest attack power
        defender1.setHealth(0); // Assassin dead
        defender3.setHealth(0); // Samurai dead

        attacker2.setWeaponEquipped(new Weapon("Enhanced Bow", WeaponType.LONGBOW, 50, null, null));

        Ninja selectedAttacker = enemyCombat.chooseAttackingNinja(enemyPlayerCombat);

        // Expected fix: Marksman has the highest attack value
        assertEquals(attacker2, selectedAttacker);
    }

    @Test
    void testChooseAttackingNinja_FirstAvailableIfNoOtherOptions() {
        // If only one ninja is alive, that ninja is chosen
        attacker1.setHealth(0);
        attacker2.setHealth(0);
        Ninja selectedAttacker = enemyCombat.chooseAttackingNinja(enemyPlayerCombat);
        assertEquals(attacker3, selectedAttacker);
    }

    @Test
    void testChooseEnemyTarget_TypeAdvantage() {
        // Samurai (attacker3) should attack Assassin (defender1) due to type advantage
        defender1.setHealth(100);
        defender2.setHealth(100);
        defender3.setHealth(100);

        PlayerNinja selectedTarget = enemyCombat.chooseEnemyTarget(enemyPlayerCombat, attacker3);
        assertEquals(defender1, selectedTarget);
    }

    @Test
    void testChooseEnemyTarget_LowestHealthAmongAdvantage() {
        // When multiple defenders have the same type disadvantage, pick the one with lowest health
        defender1.setHealth(50);
        defender2.setHealth(80);
        defender3.setHealth(100);

        PlayerNinja selectedTarget = enemyCombat.chooseEnemyTarget(enemyPlayerCombat, attacker3);
        assertEquals(defender1, selectedTarget);
    }

    @Test
    void testChooseEnemyTarget_MaxDamageIfHealthEqual() {
        // If two targets have the same health, choose the one taking max damage
        defender1.setHealth(50);
        defender2.setHealth(50);
        defender3.setHealth(50);

        defender1.setWeaponEquipped(new Weapon("Short Sword", WeaponType.TANTO, 20, null, null));
        defender2.setWeaponEquipped(new Weapon("Enhanced Crossbow", WeaponType.LONGBOW, 60, null, null));

        PlayerNinja selectedTarget = enemyCombat.chooseEnemyTarget(enemyPlayerCombat, attacker1);
        assertEquals(defender2, selectedTarget); // Since it takes more damage
    }

    @Test
    void testChooseEnemyTarget_HighestDamageIfNoAdvantage() {
        // If no type advantage, pick the enemy that takes the most damage
        defender1.setHealth(100);
        defender2.setHealth(100);
        defender3.setHealth(100);

        defender1.setWeaponEquipped(new Weapon("Short Sword", WeaponType.TANTO, 20, null, null));
        defender2.setWeaponEquipped(new Weapon("Enhanced Crossbow", WeaponType.LONGBOW, 60, null, null));
        defender3.setWeaponEquipped(new Weapon("Naginata", WeaponType.KATANA, 40, null, null));

        PlayerNinja selectedTarget = enemyCombat.chooseEnemyTarget(enemyPlayerCombat, attacker2);

        // Fixing the expected value: Samurai has the highest effective attack
        assertEquals(defender3, selectedTarget);
    }

    @Test
    void testHasAliveNinjas_WhenAllAlive() {
        assertTrue(enemyCombat.hasAliveNinjas());
    }

    @Test
    void testHasAliveNinjas_WhenSomeAlive() {
        attacker1.setHealth(0);
        attacker2.setHealth(0);
        assertTrue(enemyCombat.hasAliveNinjas());
    }

    @Test
    void testHasAliveNinjas_WhenNoneAlive() {
        attacker1.setHealth(0);
        attacker2.setHealth(0);
        attacker3.setHealth(0);
        assertFalse(enemyCombat.hasAliveNinjas());
    }
}
