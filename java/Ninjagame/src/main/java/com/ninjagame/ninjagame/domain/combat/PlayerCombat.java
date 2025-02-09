package com.ninjagame.ninjagame.domain.combat;

import com.ninjagame.ninjagame.domain.ninja.Ninja;
import com.ninjagame.ninjagame.domain.player.Player;
import lombok.Getter;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PlayerCombat {
    @Getter
    private Player player;
    @Getter
    private Ninja[] ninjas;

    public PlayerCombat(Player player, Ninja[] ninjas) {
        this.player = player;

        if (ninjas.length != 3) {
            throw new IllegalArgumentException("Must be 3 Ninjas");
        }

        this.ninjas = ninjas;
    }

    public Ninja chooseAttackingNinja(String ninjaName) {
        return getValidNinja(ninjaName, ninjas);
    }

    public Ninja chooseEnemyTarget(EnemyCombat enemyCombat, String ninjaName) {
        return getValidNinja(ninjaName, enemyCombat.getNinjas());
    }


    private Ninja getValidNinja(String name, Ninja[] ninjas) {
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
