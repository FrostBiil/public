package com.ninjagame.ninjagame.domain.player;

import com.ninjagame.ninjagame.domain.console.InputProvider;

public class InventoryManager {
    private final Player player;
    private final InputProvider inputProvider;

    public InventoryManager(Player player, InputProvider inputProvider) {
        this.player = player;
        this.inputProvider = inputProvider;
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n📦 Inventory Management");
            System.out.println("1. View weapons");
            System.out.println("2. View ninjas");
            System.out.println("3. Equip weapon to ninja");
            System.out.println("4. Remove weapon");
            System.out.println("5. Remove ninja");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice = inputProvider.getIntInput();

            switch (choice) {
                case 1:
                    player.viewWeapons();
                    break;
                case 2:
                    player.viewNinjas();
                    break;
                case 3:
                    System.out.print("Enter Ninja ID: ");
                    int ninjaId = inputProvider.getIntInput();
                    System.out.print("Enter Weapon ID: ");
                    int weaponId = inputProvider.getIntInput();
                    player.equipWeaponToNinja(ninjaId, weaponId);
                    break;
                case 4:
                    System.out.print("Enter Weapon ID to remove: ");
                    int removeWeaponId = inputProvider.getIntInput();
                    player.removeWeapon(removeWeaponId);
                    break;
                case 5:
                    System.out.print("Enter Ninja ID to remove: ");
                    int removeNinjaId = inputProvider.getIntInput();
                    player.removeNinja(removeNinjaId);
                    break;
                case 6:
                    System.out.println("Exiting inventory management.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
