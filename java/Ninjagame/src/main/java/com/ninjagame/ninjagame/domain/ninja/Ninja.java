package com.ninjagame.ninjagame.domain.ninja;

import com.ninjagame.ninjagame.domain.Rarity;
import com.ninjagame.ninjagame.domain.weapon.Weapon;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ninja {
    private int id;
    private String name;
    private NinjaClass ninjaClass;
    private double strength;
    private double health;
    private double armor;
    private double energy;
    private Rarity rarity;
    private Weapon weaponEquipped;

    public Ninja(String name, NinjaClass ninjaClass, double strength, double health, double armor, double energy, Rarity rarity, Weapon weaponEquipped) {
        this.name = name;
        this.ninjaClass = ninjaClass;
        this.strength = strength;
        this.health = health;
        this.armor = armor;
        this.energy = energy;
        this.rarity = rarity;
        this.weaponEquipped = weaponEquipped;
    }

    /**
     * Returns the weapon's damage, or 0 if no weapon is equipped.
     */
    public double getWeaponDamage() {
        return (weaponEquipped != null) ? weaponEquipped.getDamage() : 0;
    }
}
