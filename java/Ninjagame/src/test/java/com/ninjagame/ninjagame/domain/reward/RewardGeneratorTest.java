package com.ninjagame.ninjagame.domain.reward;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RewardGeneratorTest {
    private static final int ENEMY_LEVEL = 5;
    private RewardGenerator rewardGenerator;
    private Random mockRandom;

    @BeforeEach
    void setUp() {
        mockRandom = new Random(123); // ✅ Use a fixed seed for predictable results
        rewardGenerator = new RewardGenerator(mockRandom);
    }

    @Test
    void testGenerateRewardsForWin() {
        List<Reward> rewards = rewardGenerator.generateRewards(ENEMY_LEVEL, true);

        int expectedCoins = 50 + (ENEMY_LEVEL * 10); // 50 + (5 * 10) = 100
        int expectedXp = 20 + (ENEMY_LEVEL * 5); // 20 + (5 * 5) = 45

        boolean hasExpectedCoins = rewards.stream()
                .anyMatch(r -> r.getType() == RewardType.COINS && r.getAmount() == expectedCoins);

        boolean hasExpectedXp = rewards.stream()
                .anyMatch(r -> r.getType() == RewardType.EXP && r.getAmount() == expectedXp);

        assertTrue(hasExpectedCoins, "Expected " + expectedCoins + " coins, but it was not found.");
        assertTrue(hasExpectedXp, "Expected " + expectedXp + " XP, but it was not found.");
    }

    @Test
    void testGenerateRewardsForLoss() {
        int enemyLevel = 5;
        List<Reward> rewards = rewardGenerator.generateRewards(enemyLevel, false);

        int expectedCoins = (50 + (enemyLevel * 10)) / 2; // 100 / 2 = 50
        int expectedXp = (20 + (enemyLevel * 5)) / 2; // 45 / 2 = 22

        boolean hasExpectedCoins = rewards.stream()
                .anyMatch(r -> r.getType() == RewardType.COINS && r.getAmount() == expectedCoins);

        boolean hasExpectedXp = rewards.stream()
                .anyMatch(r -> r.getType() == RewardType.EXP && r.getAmount() == expectedXp);

        assertTrue(hasExpectedCoins, "Expected " + expectedCoins + " coins, but it was not found.");
        assertTrue(hasExpectedXp, "Expected " + expectedXp + " XP, but it was not found.");
    }



    @Test
    void testWeaponDropOnlyOnWin() {
        List<Reward> rewardsWhenWon = rewardGenerator.generateRewards(ENEMY_LEVEL, true);
        List<Reward> rewardsWhenLost = rewardGenerator.generateRewards(ENEMY_LEVEL, false);

        boolean hasWeaponWhenWon = rewardsWhenWon.stream().anyMatch(r -> r.getType() == RewardType.WEAPON);
        boolean hasWeaponWhenLost = rewardsWhenLost.stream().anyMatch(r -> r.getType() == RewardType.WEAPON);

        assertFalse(hasWeaponWhenLost, "Weapons should not drop when losing.");
        // We cannot assert a weapon is always dropped, since chance is 30%.
    }

    @Test
    void testNinjaDropOnlyOnWin() {
        List<Reward> rewardsWhenWon = rewardGenerator.generateRewards(ENEMY_LEVEL, true);
        List<Reward> rewardsWhenLost = rewardGenerator.generateRewards(ENEMY_LEVEL, false);

        boolean hasNinjaWhenWon = rewardsWhenWon.stream().anyMatch(r -> r.getType() == RewardType.NINJA);
        boolean hasNinjaWhenLost = rewardsWhenLost.stream().anyMatch(r -> r.getType() == RewardType.NINJA);

        assertFalse(hasNinjaWhenLost, "Ninjas should not drop when losing.");
    }

    @Test
    void testRandomWeaponAndNinjaGeneration() {
        boolean foundWeapon = false;
        boolean foundNinja = false;

        for (int i = 0; i < 100; i++) { // Run the reward generator multiple times
            List<Reward> rewards = rewardGenerator.generateRewards(5, true);

            if (rewards.stream().anyMatch(r -> r.getType() == RewardType.WEAPON)) {
                foundWeapon = true;
            }
            if (rewards.stream().anyMatch(r -> r.getType() == RewardType.NINJA)) {
                foundNinja = true;
            }

            if (foundWeapon || foundNinja) {
                break; // Stop early if at least one was found
            }
        }

        assertTrue(foundWeapon || foundNinja, "At least one of the rewards (WEAPON or NINJA) should be generated after multiple runs.");
    }


    @Test
    void testOnlyValidRewardsGenerated() {
        List<Reward> rewards = rewardGenerator.generateRewards(ENEMY_LEVEL, true);

        assertTrue(rewards.stream().allMatch(r ->
                r.getType() == RewardType.COINS ||
                r.getType() == RewardType.EXP ||
                r.getType() == RewardType.WEAPON ||
                r.getType() == RewardType.NINJA),
                "Generated rewards should only be COINS, EXP, WEAPON, or NINJA."
        );
    }
}
