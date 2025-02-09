package com.ninjagame.ninjagame.domain.combat;

import com.ninjagame.ninjagame.domain.ninja.Ninja;
import com.ninjagame.ninjagame.domain.ninja.NinjaClass;
import com.ninjagame.ninjagame.domain.player.Player;
import lombok.Getter;

public class EnemyCombat {
    @Getter
    private Player player;
    @Getter
    private Ninja[] ninjas;

    public EnemyCombat(Player player, Ninja[] ninjas) {
        this.player = player;

        if (ninjas.length != 3) {
            throw new IllegalArgumentException("Must be 3 Ninjas");
        }

        this.ninjas = ninjas;
    }

    /**
     * Chooses the best attacking ninja:
     * 1. Prioritizes a ninja with a type advantage (attack boost).
     * 2. If multiple ninjas have an advantage, selects the one with the highest effective attack.
     * 3. If no type advantage, picks a ninja of the same type as an enemy if possible.
     * 4. If no same-type match exists, picks the first available ninja.
     */
    public Ninja chooseAttackingNinja(PlayerCombat enemyCombat) {
        Ninja bestSameTypeAttacker = null;
        double highestSameTypeAttack = 0;
        Ninja bestOverallAttacker = null;
        double highestOverallAttack = 0;

        for (Ninja attacker : ninjas) {
            if (attacker.getHealth() <= 0) continue; // Skip dead ninjas

            double baseAttack = attacker.getStrength() + attacker.getWeaponDamage(); // ✅ No NullPointerException

            for (Ninja defender : enemyCombat.getNinjas()) {
                if (defender.getHealth() <= 0) continue;

                double typeMultiplier = getTypeMultiplier(attacker, defender);
                double effectiveAttack = baseAttack * typeMultiplier;

                if (attacker.getNinjaClass() == defender.getNinjaClass()) {
                    if (effectiveAttack > highestSameTypeAttack) {
                        highestSameTypeAttack = effectiveAttack;
                        bestSameTypeAttacker = attacker;
                    }
                }

                if (effectiveAttack > highestOverallAttack) {
                    highestOverallAttack = effectiveAttack;
                    bestOverallAttacker = attacker;
                }
            }
        }

        return (bestSameTypeAttacker != null) ? bestSameTypeAttacker : bestOverallAttacker;
    }


    /**
     * Chooses the best target for the selected attacker:
     * 1. Prioritizes a target weak against the attacker.
     * 2. If multiple weak targets exist, attack the lowest HP.
     * 3. If no weak target exists, attack the enemy with the highest expected damage taken.
     */
    public Ninja chooseEnemyTarget(PlayerCombat enemyCombat, Ninja attacker) {
        Ninja bestTarget = null;
        double lowestHealth = Double.MAX_VALUE;
        double maxDamage = 0;

        for (Ninja defender : enemyCombat.getNinjas()) {
            if (defender.getHealth() <= 0) continue;

            double damageMultiplier = getTypeMultiplier(attacker, defender);
            double attackDamage = attacker.getWeaponDamage() * damageMultiplier;

            if (damageMultiplier > 1.0) { // Prioritize type advantage
                if (defender.getHealth() < lowestHealth) {
                    bestTarget = defender;
                    lowestHealth = defender.getHealth();
                } else if (defender.getHealth() == lowestHealth && attackDamage > maxDamage) {
                    bestTarget = defender;
                    maxDamage = attackDamage;
                }
            }
        }

        // If no type advantage, pick the target taking the most damage
        if (bestTarget == null) {
            for (Ninja defender : enemyCombat.getNinjas()) {
                if (defender.getHealth() <= 0) continue;

                double attackDamage = attacker.getWeaponDamage();
                if (attackDamage > maxDamage) {
                    bestTarget = defender;
                    maxDamage = attackDamage;
                }
            }
        }

        return bestTarget;
    }


    /**
     * Checks if any ninja is still alive.
     */
    public boolean hasAliveNinjas() {
        for (Ninja ninja : ninjas) {
            if (ninja.getHealth() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Displays all alive ninjas in the AI's team.
     */
    public void displayAliveNinjas() {
        System.out.println("Enemy's Alive Ninjas:");
        for (Ninja ninja : ninjas) {
            if (ninja.getHealth() > 0) {
                System.out.println("- " + ninja.getName() + " (HP: " + ninja.getHealth() + ")");
            }
        }
    }

    /**
     * Gets the type multiplier based on ninja class advantages.
     */
    private double getTypeMultiplier(Ninja attacker, Ninja defender) {
        if (attacker.getNinjaClass() == NinjaClass.ASSASSIN && defender.getNinjaClass() == NinjaClass.MARKSMAN) return 1.2;
        if (attacker.getNinjaClass() == NinjaClass.MARKSMAN && defender.getNinjaClass() == NinjaClass.SAMURAI) return 1.2;
        if (attacker.getNinjaClass() == NinjaClass.SAMURAI && defender.getNinjaClass() == NinjaClass.ASSASSIN) return 1.2;
        if (attacker.getNinjaClass() == defender.getNinjaClass()) return 1.0;

        return 0.8; // If no advantage, apply a small penalty
    }
}
