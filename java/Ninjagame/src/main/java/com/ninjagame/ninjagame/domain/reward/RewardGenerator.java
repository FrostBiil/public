package com.ninjagame.ninjagame.domain.reward;

import java.util.*;

public class RewardGenerator {
    private final Random random;

    public RewardGenerator(Random random) {
        this.random = random;
    }

    public List<Reward> generateRewards(int enemyLevel, boolean playerWon) {
        List<Reward> rewards = new ArrayList<>();

        int coins = 50 + (enemyLevel * 10);
        int xp = 20 + (enemyLevel * 5);
        if (!playerWon) {
            coins /= 2;
            xp /= 2;
        }

        rewards.add(new Reward(RewardType.COINS, coins));
        rewards.add(new Reward(RewardType.EXP, xp));

        if (playerWon && random.nextDouble() < 0.3) { // 30% chance
            int weaponId = getRandomWeapon();
            rewards.add(new Reward(RewardType.WEAPON, 1, weaponId));
        }

        if (playerWon && random.nextDouble() < 0.05) { // 5% chance
            int ninjaId = getRandomNinja();
            rewards.add(new Reward(RewardType.NINJA, 1, ninjaId));
        }

        return rewards;
    }

    private int getRandomWeapon() {
        return random.nextInt(100) + 1; // Example weapon IDs
    }

    private int getRandomNinja() {
        return random.nextInt(10) + 1; // Example ninja IDs
    }
}
