package com.ninjagame.ninjagame.domain.reward;

import com.ninjagame.ninjagame.domain.player.Player;
import java.util.List;

public class RewardProcessor {
    public static void applyRewards(Player player, List<Reward> rewards) {
        if (player == null || rewards == null) {
            System.out.println("Error: Player or rewards list is null.");
            return;
        }

        for (Reward reward : rewards) {
            if (reward == null) continue;  // Skip null rewards

            switch (reward.getType()) {
                case COINS:
                    player.addCoins(reward.getAmount());
                    System.out.println("Player received " + reward.getAmount() + " coins.");
                    break;

                case EXP:
                    player.addExperience(reward.getAmount());
                    System.out.println("Player gained " + reward.getAmount() + " XP.");
                    break;

                case WEAPON:
                    if (reward.getItemId() != null && reward.getItemId() > 0) {
                        player.addWeaponToInventory(reward.getItemId());  // ✅ Only call if ID is valid
                        System.out.println("Player received weapon ID: " + reward.getItemId());
                    } else {
                        System.out.println("Error: Weapon reward is missing an ID.");
                    }
                    break;

                case NINJA:
                    if (reward.getItemId() != null && reward.getItemId() > 0) {
                        player.recruitNinja(reward.getItemId());  // ✅ Only call if ID is valid
                        System.out.println("Player recruited ninja ID: " + reward.getItemId());
                    } else {
                        System.out.println("Error: Ninja reward is missing an ID.");
                    }
                    break;

                default:
                    System.out.println("Warning: Unknown reward type: " + reward.getType());
                    break;
            }
        }
    }

}
