package com.ninjagame.ninjagame.domain.weapon;

import com.ninjagame.ninjagame.domain.player.Inventory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerWeapon extends Weapon {
    private int level;
    private Inventory inventory;
    private Weapon baseWeapon;

    public PlayerWeapon(Weapon baseWeapon, Inventory inventory) {
        super(baseWeapon.getName(), baseWeapon.getType(), baseWeapon.getDamage(), baseWeapon.getRarity(), baseWeapon.getAllowedNinjaClasses());

        this.level = 1;
        this.inventory = inventory;
        this.baseWeapon = baseWeapon;
    }

    public PlayerWeapon(int id, Weapon baseWeapon, Inventory inventory) {
        super(baseWeapon.getName(), baseWeapon.getType(), baseWeapon.getDamage(), baseWeapon.getRarity(), baseWeapon.getAllowedNinjaClasses());
        setId(id);
        this.inventory = inventory;
        this.baseWeapon = baseWeapon;
        this.level = 1;
    }

    public PlayerWeapon(Weapon baseWeapon) {
        super(baseWeapon.getName(), baseWeapon.getType(), baseWeapon.getDamage(), baseWeapon.getRarity(), baseWeapon.getAllowedNinjaClasses());
    }


    public void levelUp() {
        level++;
        setDamage(getDamage() * 1.15); // Update the damage value in the superclass
    }

    @Override
    public String toString() {
        return "PlayerWeapon{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", type=" + getType().toPrettyString() +
                ", attackPower=" + getDamage() +
                ", rarity=" + getRarity().toPrettyString() +
                ", level=" + level +
                ", baseWeapon=" + (baseWeapon != null ? baseWeapon.getName() : "None") +
                '}';
    }

}
