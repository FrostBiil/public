package com.ninjagame.ninjagame.domain.player;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Player {
    private int id;
    private String name;
    private int level;
    private int exp;
    private Inventory inventory; // No JPA annotations, just a simple reference

    public Player(String name, int level, int exp, Inventory inventory) {
        this.name = name;
        this.level = level;
        this.exp = exp;
        this.inventory = inventory;
    }
}
