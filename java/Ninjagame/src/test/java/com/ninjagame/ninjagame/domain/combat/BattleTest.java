package com.ninjagame.ninjagame.domain.combat;

import com.ninjagame.ninjagame.domain.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    private Battle battle;
    private Player player;
    private Player enemy;

    @BeforeEach
    void setUp() {
        player = new Player("Hero", 10, 100, null);
        enemy = new Player("Villain", 8, 75, null);
        battle = new Battle(player, enemy, true, "{\"gold\":100, \"xp\":50}");
    }

    @Test
    void testBattleInitialization() {
        assertEquals(player, battle.getPlayer());
        assertEquals(enemy, battle.getEnemy());
        assertTrue(battle.isPlayerWin());
        assertEquals("{\"gold\":100, \"xp\":50}", battle.getRewards());
    }

    @Test
    void testSetPlayer() {
        Player newPlayer = new Player("NewHero", 12, 150, null);
        battle.setPlayer(newPlayer);
        assertEquals(newPlayer, battle.getPlayer());
    }

    @Test
    void testSetEnemy() {
        Player newEnemy = new Player("NewVillain", 9, 80, null);
        battle.setEnemy(newEnemy);
        assertEquals(newEnemy, battle.getEnemy());
    }

    @Test
    void testSetPlayerWin() {
        battle.setPlayerWin(false);
        assertFalse(battle.isPlayerWin());
    }

    @Test
    void testSetRewards() {
        battle.setRewards("{\"gold\":200, \"xp\":100}");
        assertEquals("{\"gold\":200, \"xp\":100}", battle.getRewards());
    }
}
