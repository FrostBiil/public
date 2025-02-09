package com.ninjagame.ninjagame.domain.player;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class Inventory {
    private int id;
    private int coins;
    private int gems;
    private List<PlayerWeapon> weapons;
    private List<PlayerNinja> ninjas;

    public Inventory(int coins, int gems) {
        this.coins = coins;
        this.gems = gems;
    }
}
