package com.ninjagame.ninjagame.domain.reward;

import com.ninjagame.ninjagame.domain.player.Inventory;
import com.ninjagame.ninjagame.domain.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RewardTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer", 1, 0, new Inventory());
    }

    @Test
    void testRewardCreationForCoins() {
        Reward reward = new Reward(RewardType.COINS, 100);
        assertEquals(RewardType.COINS, reward.getType());
        assertEquals(100, reward.getAmount());
        assertNull(reward.getItemId());
    }

    @Test
    void testRewardCreationForXP() {
        Reward reward = new Reward(RewardType.EXP, 50);
        assertEquals(RewardType.EXP, reward.getType());
        assertEquals(50, reward.getAmount());
        assertNull(reward.getItemId());
    }

    @Test
    void testRewardCreationForWeapon() {
        Reward reward = new Reward(RewardType.WEAPON,1, 1);
        assertEquals(RewardType.WEAPON, reward.getType());
        assertEquals(1, reward.getItemId());
    }

    @Test
    void testRewardCreationForNinja() {
        Reward reward = new Reward(RewardType.NINJA,1, 2);
        assertEquals(RewardType.NINJA, reward.getType());
        assertEquals(2, reward.getItemId());
    }


    @Test
    void testApplyRewardsForCoinsAndXP() {
        List<Reward> rewards = List.of(
                new Reward(RewardType.COINS, 200),
                new Reward(RewardType.EXP, 50)
        );

        RewardProcessor.applyRewards(player, rewards);

        assertEquals(200, player.getInventory().getCoins());
        assertEquals(50, player.getExp());
    }

    @Test
    void testApplyRewardsForWeapon() {
        List<Reward> rewards = List.of(
                new Reward(RewardType.WEAPON,1, 1)
        );

        RewardProcessor.applyRewards(player, rewards);

        assertFalse(player.getInventory().getWeapons().isEmpty());
        assertEquals(1, player.getInventory().getWeapons().size());
    }


    @Test
    void testApplyRewardsForNinja() {
        List<Reward> rewards = List.of(
                new Reward(RewardType.NINJA,1, 2)
        );

        RewardProcessor.applyRewards(player, rewards);

        assertFalse(player.getInventory().getNinjas().isEmpty());
        assertEquals(1, player.getInventory().getNinjas().size());
    }


    @Test
    void testApplyRewardsHandlesInvalidWeapon() {
        List<Reward> rewards = List.of(
                new Reward(RewardType.WEAPON, 1,-1) // Invalid weapon ID
        );

        RewardProcessor.applyRewards(player, rewards);

        assertTrue(player.getInventory().getWeapons().isEmpty()); // No weapon added
    }

    @Test
    void testApplyRewardsHandlesInvalidNinja() {
        List<Reward> rewards = List.of(
                new Reward(RewardType.NINJA, 1,-5) // Invalid ninja ID
        );

        RewardProcessor.applyRewards(player, rewards);

        assertTrue(player.getInventory().getNinjas().isEmpty()); // No ninja added
    }

    @Test
    void testApplyRewardsWithNullReward() {
        List<Reward> rewards = new ArrayList<>();
        rewards.add(null);

        assertDoesNotThrow(() -> RewardProcessor.applyRewards(player, rewards)); // Should not crash
    }
}
