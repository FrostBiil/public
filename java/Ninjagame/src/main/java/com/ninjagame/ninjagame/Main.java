package com.ninjagame.ninjagame;

import com.ninjagame.ninjagame.domain.combat.EnemyCombat;
import com.ninjagame.ninjagame.domain.combat.PlayerCombat;
import com.ninjagame.ninjagame.domain.ninja.Ninja;
import com.ninjagame.ninjagame.domain.ninja.NinjaClass;
import com.ninjagame.ninjagame.domain.player.Player;
import com.ninjagame.ninjagame.domain.weapon.Weapon;
import com.ninjagame.ninjagame.domain.weapon.WeaponType;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create players
        Player player = new Player("Player1", 10, 500, null);
        Player enemy = new Player("EnemyAI", 10, 500, null);

        // Create player ninjas
        Ninja playerNinja1 = new Ninja("Shadow Assassin", NinjaClass.ASSASSIN, 80, 100, 20, 100, null,
                new Weapon("Dagger", WeaponType.TANTO, 30, null, null));
        Ninja playerNinja2 = new Ninja("Silent Marksman", NinjaClass.MARKSMAN, 70, 90, 15, 90, null,
                new Weapon("Bow", WeaponType.LONGBOW, 40, null, null));
        Ninja playerNinja3 = new Ninja("Fierce Samurai", NinjaClass.SAMURAI, 100, 110, 25, 110, null,
                new Weapon("Katana", WeaponType.KATANA, 50, null, null));

        // Create enemy ninjas
        // Create enemy ninjas
        Ninja enemyNinja1 = new Ninja("Rival Assassin", NinjaClass.ASSASSIN, 75, 100, 20, 100, null,
                new Weapon("Rusty Dagger", WeaponType.TANTO, 30, null, null));
        Ninja enemyNinja2 = new Ninja("Rival Marksman", NinjaClass.MARKSMAN, 65, 85, 15, 90, null,
                new Weapon("Old Bow", WeaponType.LONGBOW, 45, null, null));
        Ninja enemyNinja3 = new Ninja("Rival Samurai", NinjaClass.SAMURAI, 95, 105, 25, 110, null,
                new Weapon("Wooden Katana", WeaponType.KATANA, 30, null, null));

        // Initialize combat classes
        PlayerCombat playerCombat = new PlayerCombat(player, new Ninja[]{playerNinja1, playerNinja2, playerNinja3});
        EnemyCombat enemyCombat = new EnemyCombat(enemy, new Ninja[]{enemyNinja1, enemyNinja2, enemyNinja3});

        // Start the game loop
        while (playerCombat.hasAliveNinjas() && enemyCombat.hasAliveNinjas()) {
            System.out.println("\n--- PLAYER TURN ---");

            // Display player ninjas
            playerCombat.displayAliveNinjas();
            enemyCombat.displayAliveNinjas();

            // Select an attacking ninja
            Ninja attackingNinja;
            while (true) {
                System.out.print("Enter the name of your attacking ninja: ");
                String ninjaName = scanner.nextLine().trim();
                try {
                    attackingNinja = playerCombat.chooseAttackingNinja(ninjaName);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid ninja! Try again.");
                }
            }

            // Select an enemy target
            Ninja targetNinja;
            while (true) {
                System.out.print("Enter the name of the enemy ninja to attack: ");
                String targetName = scanner.nextLine().trim();
                try {
                    targetNinja = playerCombat.chooseEnemyTarget(enemyCombat, targetName);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid target! Try again.");
                }
            }

            // Attack
            double damage = attackingNinja.getWeaponEquipped().getDamage();
            targetNinja.setHealth(targetNinja.getHealth() - damage);
            System.out.println(attackingNinja.getName() + " attacks " + targetNinja.getName() + " for " + damage + " damage!");

            // Check if the enemy is dead
            if (targetNinja.getHealth() <= 0) {
                System.out.println(targetNinja.getName() + " has been defeated!");
            }

            // Check if the enemy has ninjas left
            if (!enemyCombat.hasAliveNinjas()) {
                System.out.println("\n🎉 You won the battle!");
                break;
            }

            // Enemy AI turn
            System.out.println("\n--- ENEMY TURN ---");
            Ninja enemyAttacker = enemyCombat.chooseAttackingNinja(playerCombat);
            Ninja enemyTarget = enemyCombat.chooseEnemyTarget(playerCombat, enemyAttacker);

            if (enemyAttacker != null && enemyTarget != null) {
                double enemyDamage = (enemyAttacker.getWeaponEquipped() != null) ? enemyAttacker.getWeaponEquipped().getDamage() : 0;
                enemyTarget.setHealth(enemyTarget.getHealth() - enemyDamage);
                System.out.println(enemyAttacker.getName() + " attacks " + enemyTarget.getName() + " for " + enemyDamage + " damage!");

                if (enemyTarget.getHealth() <= 0) {
                    System.out.println(enemyTarget.getName() + " has been defeated!");
                }
            }

            // Check if the player has ninjas left
            if (!playerCombat.hasAliveNinjas()) {
                System.out.println("\n💀 You lost the battle...");
                break;
            }
        }

        scanner.close();
    }
}
