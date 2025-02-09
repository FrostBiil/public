package com.ninjagame.ninjagame.domain.reward;

public class Reward {
    private RewardType type;
    private int amount;  // Used for coins/exp
    private Integer itemId; // Used for weapons/ninjas (nullable)

    public Reward(RewardType type, int amount) {
        this.type = type;
        this.amount = amount;
        this.itemId = null;  // Not used for coins/exp
    }

    public Reward(RewardType type, int amount, int itemId) {
        this.type = type;
        this.amount = 1;  // Always 1 for item rewards
        this.itemId = itemId;
    }

    public RewardType getType() { return type; }
    public int getAmount() { return amount; }
    public Integer getItemId() { return itemId; }
}
