package com.ninjagame.ninjagame.persistence;

import com.ninjagame.ninjagame.domain.Rarity;
import com.ninjagame.ninjagame.domain.ninja.Ninja;
import com.ninjagame.ninjagame.domain.ninja.NinjaClass;
import com.ninjagame.ninjagame.domain.player.Inventory;
import com.ninjagame.ninjagame.domain.ninja.PlayerNinja;
import com.ninjagame.ninjagame.domain.weapon.PlayerWeapon;

import java.util.*;

public class NinjaDatabase {
    private static final Map<Integer, Ninja> ninjaMap = new HashMap<>();
    private static final Random RANDOM = new Random();

    static {
        // Base ninjas (no levels, default stats)
        ninjaMap.put(1, new Ninja("Hanzo", NinjaClass.SAMURAI, 25.0, 100.0, 10.0, 100.0, Rarity.COMMON, null));
        ninjaMap.put(2, new Ninja("Takeda", NinjaClass.SAMURAI, 30.0, 110.0, 15.0, 100.0, Rarity.UNCOMMON, null));
        ninjaMap.put(3, new Ninja("Jin", NinjaClass.ASSASSIN, 35.0, 90.0, 5.0, 100.0, Rarity.RARE, null));
        ninjaMap.put(4, new Ninja("Kunoichi", NinjaClass.ASSASSIN, 28.0, 85.0, 7.0, 100.0, Rarity.EPIC, null));
        ninjaMap.put(5, new Ninja("Ryu", NinjaClass.MARKSMAN, 20.0, 95.0, 8.0, 100.0, Rarity.LEGENDARY, null));
    }

    public static PlayerNinja getNinjaById(int ninjaId, Inventory inventory) {
        Ninja baseNinja = ninjaMap.get(ninjaId);
        if (baseNinja == null) {
            return null; // If the ninja does not exist
        }

        // Assign a default weapon (or none)
        PlayerWeapon defaultWeapon = new PlayerWeapon(WeaponDatabase.getRandomWeapon(inventory), inventory);

        // Return a PlayerNinja (customized version of Ninja)
        return new PlayerNinja(
                baseNinja.getName(),
                baseNinja.getNinjaClass(),
                baseNinja.getStrength(),
                baseNinja.getHealth(),
                baseNinja.getArmor(),
                baseNinja.getEnergy(),
                baseNinja.getRarity(),
                defaultWeapon // Default weapon assigned
        );
    }

    public static PlayerNinja getRandomNinja(Inventory inventory) {
        List<Ninja> ninjas = new ArrayList<>(ninjaMap.values());
        if (ninjas.isEmpty()) return null;

        Ninja baseNinja = ninjas.get(RANDOM.nextInt(ninjas.size()));
        PlayerWeapon defaultWeapon = new PlayerWeapon(WeaponDatabase.getRandomWeapon(inventory), inventory);

        return new PlayerNinja(
                baseNinja.getName(),
                baseNinja.getNinjaClass(),
                baseNinja.getStrength(),
                baseNinja.getHealth(),
                baseNinja.getArmor(),
                baseNinja.getEnergy(),
                baseNinja.getRarity(),
                defaultWeapon
        );
    }

    public static PlayerNinja getRandomNinjaByRarity(Rarity rarity, Inventory inventory) {
        List<Ninja> filteredNinjas = ninjaMap.values().stream()
                .filter(ninja -> ninja.getRarity() == rarity)
                .toList();

        if (filteredNinjas.isEmpty()) return null;

        Ninja baseNinja = filteredNinjas.get(RANDOM.nextInt(filteredNinjas.size()));
        PlayerWeapon defaultWeapon = new PlayerWeapon(WeaponDatabase.getRandomWeapon(inventory), inventory);

        return new PlayerNinja(
                baseNinja.getName(),
                baseNinja.getNinjaClass(),
                baseNinja.getStrength(),
                baseNinja.getHealth(),
                baseNinja.getArmor(),
                baseNinja.getEnergy(),
                baseNinja.getRarity(),
                defaultWeapon
        );
    }
}
