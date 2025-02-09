package com.ninjagame.ninjagame.domain.combat;

import com.ninjagame.ninjagame.domain.player.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Battle {
    private int id;
    private Player player;
    private Player enemy;
    private boolean playerWin;
    private String rewards; // Could be JSON-like data

    public Battle(Player player, Player enemy, boolean playerWin, String rewards) {
        this.player = player;
        this.enemy = enemy;
        this.playerWin = playerWin;
        this.rewards = rewards;
    }
}