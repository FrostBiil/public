package com.ninjagame.ninjagame.domain.reward;

import com.ninjagame.ninjagame.domain.player.Inventory;
import com.ninjagame.ninjagame.domain.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RewardProcessorTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer", 1, 0, new Inventory());
    }

    @Test
    void testApplyCoinsAndExperienceRewards() {
        List<Reward> rewards = List.of(
                new Reward(RewardType.COINS, 200),
                new Reward(RewardType.EXP, 50)
        );

        RewardProcessor.applyRewards(player, rewards);

        assertEquals(200, player.getInventory().getCoins(), "Player should have received 200 coins.");
        assertEquals(50, player.getExp(), "Player should have received 50 XP.");
    }

    @Test
    void testApplyWeaponRewardWithValidId() {
        List<Reward> rewards = List.of(new Reward(RewardType.WEAPON, 1, 1)); // Valid weapon ID

        RewardProcessor.applyRewards(player, rewards);

        assertFalse(player.getInventory().getWeapons().isEmpty(), "Weapon should be added to inventory.");
    }

    @Test
    void testApplyNinjaRewardWithValidId() {
        List<Reward> rewards = List.of(new Reward(RewardType.NINJA,1, 2)); // Valid ninja ID

        RewardProcessor.applyRewards(player, rewards);

        assertFalse(player.getInventory().getNinjas().isEmpty(), "Ninja should be added to inventory.");
    }

    @Test
    void testApplyWeaponRewardWithInvalidId() {
        List<Reward> rewards = List.of(new Reward(RewardType.WEAPON,1, -1)); // Invalid weapon ID

        RewardProcessor.applyRewards(player, rewards);

        assertTrue(player.getInventory().getWeapons().isEmpty(), "Invalid weapon ID should not be added.");
    }

    @Test
    void testApplyNinjaRewardWithInvalidId() {
        List<Reward> rewards = List.of(new Reward(RewardType.NINJA,1, -5)); // Invalid ninja ID

        RewardProcessor.applyRewards(player, rewards);

        assertTrue(player.getInventory().getNinjas().isEmpty(), "Invalid ninja ID should not be added.");
    }

    @Test
    void testApplyRewardsWithNullPlayer() {
        assertDoesNotThrow(() -> RewardProcessor.applyRewards(null, List.of(new Reward(RewardType.COINS, 100))),
                "Applying rewards to a null player should not throw an error.");
    }

    @Test
    void testApplyRewardsWithNullRewardsList() {
        assertDoesNotThrow(() -> RewardProcessor.applyRewards(player, null),
                "Applying null rewards should not throw an error.");
    }

    @Test
    void testApplyRewardsWithEmptyList() {
        assertDoesNotThrow(() -> RewardProcessor.applyRewards(player, List.of()),
                "Applying an empty rewards list should not throw an error.");
    }
}
