package com.ninjagame.ninjagame.domain.combat;

import com.ninjagame.ninjagame.domain.ninja.Ninja;
import com.ninjagame.ninjagame.domain.ninja.PlayerNinja;
import com.ninjagame.ninjagame.domain.player.Player;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PlayerCombat {
    @Getter
    private Player player;
    @Getter
    private PlayerNinja[] ninjas;

    public PlayerCombat(Player player, PlayerNinja[] ninjas) {
        this.player = player;

        if (ninjas.length != 3) {
            throw new IllegalArgumentException("Must be 3 Ninjas");
        }

        this.ninjas = ninjas;
    }

    public PlayerNinja chooseAttackingNinja(String ninjaName) {
        return getValidNinja(ninjaName, ninjas);
    }

    public PlayerNinja chooseEnemyTarget(EnemyCombat enemyCombat, String ninjaName) {
        return getValidNinja(ninjaName, enemyCombat.getNinjas());
    }


    private PlayerNinja getValidNinja(String name, PlayerNinja[] ninjas) {
        return Arrays.stream(ninjas)
                .filter(n -> n.getName().equals(name) && n.getHealth() > 0)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ninja selection!"));
    }


    private String getAliveNinjasAsString(Ninja[] ninjas) {
        return Arrays.stream(ninjas)
                .filter(n -> n.getHealth() > 0)
                .map(Ninja::getName)
                .collect(Collectors.joining(", "));
    }


    public void displayAliveNinjas() {
        System.out.println("Alive Ninjas:");
        for (Ninja ninja : ninjas) {
            if (ninja.getHealth() > 0) {
                System.out.println("- " + ninja.getName() + " (HP: " + ninja.getHealth() + ")");
            }
        }
    }

    public boolean hasAliveNinjas() {
        for (Ninja ninja : ninjas) {
            if (ninja.getHealth() > 0) {
                return true;
            }
        }
        return false;
    }
}
