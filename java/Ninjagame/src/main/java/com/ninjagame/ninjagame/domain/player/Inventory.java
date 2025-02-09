package com.ninjagame.ninjagame.domain.player;

import com.ninjagame.ninjagame.domain.ninja.PlayerNinja;
import com.ninjagame.ninjagame.domain.weapon.PlayerWeapon;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class Inventory {
    private int id;
    private int coins;
    private int gems;
    private List<PlayerWeapon> weapons = new ArrayList<>();
    private List<PlayerNinja> ninjas = new ArrayList<>();

    public Inventory(int coins, int gems) {
        this.coins = coins;
        this.gems = gems;
        this.weapons = new ArrayList<>();
        this.ninjas = new ArrayList<>();
    }

    public void addWeapon(PlayerWeapon newWeapon) {
        if (newWeapon != null) {
            weapons.add(newWeapon);
        }
    }

    public void addNinja(PlayerNinja newNinja) {
        if (newNinja != null) {
            ninjas.add(newNinja);
        }
    }

    public List<PlayerWeapon> getWeapons() {
        if (weapons == null) {
            weapons = new ArrayList<>();
        }

        return weapons;
    }

    public List<PlayerNinja> getNinjas() {
        if (ninjas == null) {
            ninjas = new ArrayList<>();
        }

        return ninjas;
    }

    public List<PlayerWeapon> getWeaponsSortedByRarity() {
        return weapons.stream()
                .sorted(Comparator.comparing(w -> w.getBaseWeapon().getRarity()))
                .collect(Collectors.toList());
    }

    public List<PlayerNinja> getNinjasSortedByClass() {
        return ninjas.stream()
                .sorted(Comparator.comparing(n -> n.getNinjaClass().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", coins=" + coins +
                ", gems=" + gems +
                ", weaponsCount=" + (weapons != null ? weapons.size() : 0) +
                ", ninjasCount=" + (ninjas != null ? ninjas.size() : 0) +
                '}';
    }

}
