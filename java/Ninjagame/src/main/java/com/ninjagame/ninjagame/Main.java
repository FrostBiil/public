package com.ninjagame.ninjagame;

import com.ninjagame.ninjagame.domain.combat.EnemyCombat;
import com.ninjagame.ninjagame.domain.combat.PlayerCombat;
import com.ninjagame.ninjagame.domain.ninja.NinjaClass;
import com.ninjagame.ninjagame.domain.ninja.PlayerNinja;
import com.ninjagame.ninjagame.domain.player.Inventory;
import com.ninjagame.ninjagame.domain.player.Player;
import com.ninjagame.ninjagame.domain.weapon.PlayerWeapon;
import com.ninjagame.ninjagame.domain.weapon.Weapon;
import com.ninjagame.ninjagame.domain.weapon.WeaponType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create player and enemy
        Player player = new Player("Player1", 10, 500, new Inventory(0, 0));
        Player enemy = new Player("EnemyAI", 10, 500, new Inventory(0, 0));

        // Create player's ninja inventory
        player.getInventory().addNinja(new PlayerNinja("Shadow Assassin", NinjaClass.ASSASSIN, 80, 100, 20, 100, null,
                new PlayerWeapon(new Weapon("Dagger", WeaponType.TANTO, 30, null, null))));
        player.getInventory().addNinja(new PlayerNinja("Silent Marksman", NinjaClass.MARKSMAN, 70, 90, 15, 90, null,
                new PlayerWeapon(new Weapon("Bow", WeaponType.LONGBOW, 40, null, null))));
        player.getInventory().addNinja(new PlayerNinja("Fierce Samurai", NinjaClass.SAMURAI, 100, 110, 25, 110, null,
                new PlayerWeapon(new Weapon("Katana", WeaponType.KATANA, 50, null, null))));
        player.getInventory().addNinja(new PlayerNinja("Stealthy Rogue", NinjaClass.ASSASSIN, 60, 80, 18, 90, null,
                new PlayerWeapon(new Weapon("Shuriken", WeaponType.SHURIKEN, 25, null, null))));

        // Enemy's ninjas
        PlayerNinja enemyNinja1 = new PlayerNinja("Rival Assassin", NinjaClass.ASSASSIN, 75, 100, 20, 100, null,
                new PlayerWeapon(new Weapon("Rusty Dagger", WeaponType.TANTO, 30, null, null)));
        PlayerNinja enemyNinja2 = new PlayerNinja("Rival Marksman", NinjaClass.MARKSMAN, 65, 85, 15, 90, null,
                new PlayerWeapon(new Weapon("Old Bow", WeaponType.LONGBOW, 45, null, null)));
        PlayerNinja enemyNinja3 = new PlayerNinja("Rival Samurai", NinjaClass.SAMURAI, 95, 105, 25, 110, null,
                new PlayerWeapon(new Weapon("Wooden Katana", WeaponType.KATANA, 30, null, null)));

        // Player selects 3 ninjas
        PlayerNinja[] selectedNinjas = selectNinjasForBattle(scanner, player);

        // Initialize Combat
        PlayerCombat playerCombat = new PlayerCombat(player, selectedNinjas);
        EnemyCombat enemyCombat = new EnemyCombat(enemy, new PlayerNinja[]{enemyNinja1, enemyNinja2, enemyNinja3});

        // Battle Loop
        while (playerCombat.hasAliveNinjas() && enemyCombat.hasAliveNinjas()) {
            System.out.println("\n--- PLAYER TURN ---");
            playerCombat.displayAliveNinjas();
            enemyCombat.displayAliveNinjas();

            PlayerNinja attackingNinja = selectAttackingNinja(scanner, playerCombat);
            PlayerNinja targetNinja = selectTargetNinja(scanner, enemyCombat);

            double damage = attackingNinja.getWeaponEquipped().getDamage();
            targetNinja.setHealth(targetNinja.getHealth() - damage);
            System.out.println(attackingNinja.getName() + " attacks " + targetNinja.getName() + " for " + damage + " damage!");

            if (targetNinja.getHealth() <= 0) {
                System.out.println(targetNinja.getName() + " has been defeated!");
            }

            if (!enemyCombat.hasAliveNinjas()) {
                System.out.println("\nYou won the battle!");
                break;
            }

            // Enemy Turn
            System.out.println("\n--- ENEMY TURN ---");
            PlayerNinja enemyAttacker = enemyCombat.chooseAttackingNinja(playerCombat);
            PlayerNinja enemyTarget = enemyCombat.chooseEnemyTarget(playerCombat, enemyAttacker);

            if (enemyAttacker != null && enemyTarget != null) {
                double enemyDamage = enemyAttacker.getWeaponEquipped().getDamage();
                enemyTarget.setHealth(enemyTarget.getHealth() - enemyDamage);
                System.out.println(enemyAttacker.getName() + " attacks " + enemyTarget.getName() + " for " + enemyDamage + " damage!");

                if (enemyTarget.getHealth() <= 0) {
                    System.out.println(enemyTarget.getName() + " has been defeated!");
                }
            }

            if (!playerCombat.hasAliveNinjas()) {
                System.out.println("\nYou lost the battle...");
                break;
            }
        }

        scanner.close();
    }

    private static PlayerNinja selectTargetNinja(Scanner scanner, EnemyCombat enemyCombat) {
        while (true) {
            System.out.println("Select an enemy ninja to attack:");
            enemyCombat.displayAliveNinjas();
            System.out.print("Enter ninja name: ");
            String targetName = scanner.nextLine().trim();

            for (PlayerNinja ninja : enemyCombat.getNinjas()) {
                if (ninja.getName().equalsIgnoreCase(targetName)) {
                    return ninja;
                }
            }
            System.out.println("Invalid target! Try again.");
        }
    }

    private static PlayerNinja[] selectNinjasForBattle(Scanner scanner, Player player) {
        List<PlayerNinja> availableNinjas = player.getInventory().getNinjas();
        List<PlayerNinja> selectedNinjas = new ArrayList<>();

        System.out.println("\nSelect 3 ninjas for battle (Stats shown):");
        for (int i = 0; i < availableNinjas.size(); i++) {
            PlayerNinja ninja = availableNinjas.get(i);
            System.out.printf("%d. %s (Class: %s | Strength: %.0f | Armor: %.0f | Weapon: %s | Damage: %.1f)%n",
                    i + 1, ninja.getName(), ninja.getNinjaClass(),
                    (double) ninja.getStrength(), (double) ninja.getArmor(),
                    (ninja.getWeaponEquipped() != null ? ninja.getWeaponEquipped().getName() : "None"),
                    (ninja.getWeaponEquipped() != null ? ninja.getWeaponEquipped().getDamage() : 0.0));
        }

        while (selectedNinjas.size() < 3) {
            System.out.print("Enter the number of the ninja to select: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (choice >= 0 && choice < availableNinjas.size() && !selectedNinjas.contains(availableNinjas.get(choice))) {
                    selectedNinjas.add(availableNinjas.get(choice));
                    System.out.println(availableNinjas.get(choice).getName() + " added to your team.");
                } else {
                    System.out.println("Invalid selection. Choose a different ninja.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
        return selectedNinjas.toArray(new PlayerNinja[0]);
    }



    private static PlayerNinja selectAttackingNinja(Scanner scanner, PlayerCombat playerCombat) {
        while (true) {
            System.out.println("Select your attacking ninja:");
            playerCombat.displayAliveNinjas();
            System.out.print("Enter ninja name: ");
            String ninjaName = scanner.nextLine().trim();
            try {
                return playerCombat.chooseAttackingNinja(ninjaName);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid ninja! Try again.");
            }
        }
    }

}
