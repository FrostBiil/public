package com.ninjagame.ninjagame.domain.player;

import com.ninjagame.ninjagame.domain.Rarity;
import com.ninjagame.ninjagame.domain.ninja.Ninja;
import com.ninjagame.ninjagame.domain.ninja.NinjaClass;
import com.ninjagame.ninjagame.domain.weapon.Weapon;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlayerNinja extends Ninja {
    public PlayerNinja(String name, NinjaClass ninjaClass, double strength, double health, double armor, double energy, Rarity rarity, Weapon weaponEquipped) {
        super(name, ninjaClass, strength, health, armor, energy, rarity, weaponEquipped);
    }
}
