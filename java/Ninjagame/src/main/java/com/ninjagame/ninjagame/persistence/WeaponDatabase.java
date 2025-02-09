package com.ninjagame.ninjagame.persistence;

import com.ninjagame.ninjagame.domain.Rarity;
import com.ninjagame.ninjagame.domain.player.Inventory;
import com.ninjagame.ninjagame.domain.weapon.PlayerWeapon;
import com.ninjagame.ninjagame.domain.weapon.Weapon;
import com.ninjagame.ninjagame.domain.weapon.WeaponType;

import java.util.*;

public class WeaponDatabase {
    private static final Map<Integer, Weapon> weaponMap = new HashMap<>();
    private static final Random RANDOM = new Random();

    static {
        weaponMap.put(1, new Weapon("Shuriken", WeaponType.SHURIKEN, 10.5, Rarity.COMMON, null));
        weaponMap.put(2, new Weapon("Katana", WeaponType.KATANA, 25.0, Rarity.RARE, null));
        weaponMap.put(3, new Weapon("Kunai", WeaponType.KUNAI, 15.0, Rarity.UNCOMMON, null));
        weaponMap.put(4, new Weapon("Bow", WeaponType.LONGBOW, 20.0, Rarity.RARE, null));
        weaponMap.put(5, new Weapon("Naginata", WeaponType.NAGINATA, 30.0, Rarity.LEGENDARY, null));
    }

    public static PlayerWeapon getWeaponById(int itemId, Inventory inventory) {
        Weapon baseWeapon = weaponMap.get(itemId);
        return (baseWeapon != null) ? new PlayerWeapon(baseWeapon, inventory) : null;
    }

    public static PlayerWeapon getRandomWeapon(Inventory inventory) {
        List<Weapon> weapons = new ArrayList<>(weaponMap.values());
        if (weapons.isEmpty()) return null;

        Weapon baseWeapon = weapons.get(RANDOM.nextInt(weapons.size()));
        return new PlayerWeapon(baseWeapon, inventory);
    }

    public static PlayerWeapon getRandomWeaponByRarity(Rarity rarity, Inventory inventory) {
        List<Weapon> filteredWeapons = weaponMap.values().stream()
                .filter(weapon -> weapon.getRarity() == rarity)
                .toList();

        if (filteredWeapons.isEmpty()) return null;

        Weapon baseWeapon = filteredWeapons.get(RANDOM.nextInt(filteredWeapons.size()));
        return new PlayerWeapon(baseWeapon, inventory);
    }
}
