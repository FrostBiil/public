package com.ninjagame.ninjagame.domain.player;

import com.ninjagame.ninjagame.domain.ninja.PlayerNinja;
import com.ninjagame.ninjagame.domain.weapon.PlayerWeapon;
import com.ninjagame.ninjagame.persistence.NinjaDatabase;
import com.ninjagame.ninjagame.persistence.WeaponDatabase;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Player {
    private int id;
    private String name;
    private int level;
    private int exp;
    private Inventory inventory = new Inventory();

    public Player(String name, int level, int exp, Inventory inventory) {
        this.name = name;
        this.level = level;
        this.exp = exp;
        this.inventory = (inventory != null) ? inventory : new Inventory();
    }

    public void addCoins(int amount) {
        inventory.setCoins(inventory.getCoins() + amount);
    }

    public void addExperience(int amount) {
        exp += amount;

        while (checkLevelUp()) {  // Ensure multiple level-ups if needed
            levelUp();
        }
    }

    private boolean checkLevelUp() {
        return exp >= 100;  // Returns true if exp is enough to level up
    }

    private void levelUp() {
        while (exp >= 100) {
            exp -= 100;
            level++;
        }
    }

    public void addWeaponToInventory(Integer itemId) {
        if (itemId == null) {
            System.out.println("Invalid weapon ID");
            return;
        }

        PlayerWeapon newWeapon = WeaponDatabase.getWeaponById(itemId, inventory); // Fetch weapon details
        if (newWeapon != null) {
            inventory.addWeapon(newWeapon);  // Add to player's inventory
            System.out.println("Added weapon: " + newWeapon.getName());
        } else {
            System.out.println("Weapon not found!");
        }
    }

    public void recruitNinja(Integer ninjaId) {
        if (ninjaId == null) {
            System.out.println("Invalid ninja ID");
            return;
        }

        PlayerNinja newNinja = NinjaDatabase.getNinjaById(ninjaId, inventory); // Fetch ninja details
        if (newNinja != null) {
            inventory.addNinja(newNinja); // Add to the player's team or storage
            System.out.println("Recruited ninja: " + newNinja.getName());
        } else {
            System.out.println("Ninja not found!");
        }
    }

    public void viewWeapons() {
        System.out.println("Weapons in inventory:");
        if (inventory.getWeapons().isEmpty()) {
            System.out.println("No weapons available.");
        } else {
            for (PlayerWeapon weapon : inventory.getWeapons()) {
                System.out.println("- " + weapon.getName() + " (Level " + weapon.getLevel() + ")");
            }
        }
    }

    public void viewNinjas() {
        System.out.println("Ninjas in inventory:");
        if (inventory.getNinjas().isEmpty()) {
            System.out.println("No ninjas available.");
        } else {
            for (PlayerNinja ninja : inventory.getNinjas()) {
                System.out.println("- " + ninja.getName() + " (" + ninja.getNinjaClass() + ")");
            }
        }
    }

    public void equipWeaponToNinja(int ninjaId, int weaponId) {
        PlayerNinja ninja = inventory.getNinjas().stream()
                .filter(n -> n.getId() == ninjaId)
                .findFirst()
                .orElse(null);

        PlayerWeapon weapon = inventory.getWeapons().stream()
                .filter(w -> w.getId() == weaponId)
                .findFirst()
                .orElse(null);

        if (ninja == null || weapon == null) {
            System.out.println("Error: Invalid Ninja or Weapon ID.");
            return;
        }

        ninja.setWeaponEquipped(weapon);
    }


    public void removeWeapon(int weaponId) {
        boolean removed = inventory.getWeapons().removeIf(w -> w.getId() == weaponId);
        if (removed) {
            System.out.println("Weapon removed from inventory.");
        } else {
            System.out.println("Weapon not found!");
        }
    }

    public void removeNinja(int ninjaId) {
        boolean removed = inventory.getNinjas().removeIf(n -> n.getId() == ninjaId);
        if (removed) {
            System.out.println("Ninja removed from inventory.");
        } else {
            System.out.println("Ninja not found!");
        }
    }

    public void setInventory(Inventory inventory) {
        this.inventory = (inventory != null) ? inventory : new Inventory();
    }
}
