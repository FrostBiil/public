package com.ninjagame.ninjagame.domain.player;

import com.ninjagame.ninjagame.domain.Rarity;
import com.ninjagame.ninjagame.domain.weapon.Weapon;
import com.ninjagame.ninjagame.domain.weapon.WeaponType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerWeapon {
    private int id;
    private String name;
    private WeaponType type;
    private double attackPower;
    private Rarity rarity;
    private int level;
    private Inventory inventory;
    private Weapon baseWeapon;

    public PlayerWeapon(Weapon baseWeapon, Inventory inventory) {
        this.name = baseWeapon.getName();
        this.type = baseWeapon.getType();
        this.attackPower = baseWeapon.getDamage();
        this.rarity = baseWeapon.getRarity();
        this.level = 1;
        this.inventory = inventory;  // Link to player's inventory
        this.baseWeapon = baseWeapon;
    }

    public void levelUp() {
        level++;
        attackPower *= 1.15;  // Example scaling formula
    }
}
