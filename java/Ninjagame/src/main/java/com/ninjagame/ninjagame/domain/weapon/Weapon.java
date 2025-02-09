package com.ninjagame.ninjagame.domain.weapon;

import com.ninjagame.ninjagame.domain.Rarity;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor  // Required for Hibernate
public class Weapon {
    private int id;
    private String name;
    private WeaponType type;
    private double damage;
    private Rarity rarity;
    private List<String> allowedNinjaClasses;  // Change to String list for storage

    public Weapon(String name, WeaponType type, double damage, Rarity rarity, List<String> allowedNinjaClasses) {
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.rarity = rarity;
        this.allowedNinjaClasses = allowedNinjaClasses;
    }

    public double getAttackPower() {
        return getDamage();
    }
}
