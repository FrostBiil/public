package com.ninjagame.ninjagame.domain;

public enum Rarity {
    COMMON, UNCOMMON, RARE, EPIC, LEGENDARY, MYTHIC, EXOTIC, UNIQUE;

    public static Rarity safeRarityConversion(String rarityString) {
        try {
            return Rarity.valueOf(rarityString.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return Rarity.COMMON; // Default to COMMON if invalid or NULL
        }
    }

    public String toPrettyString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    public boolean isHigherThan(Rarity other) {
        return this.ordinal() > other.ordinal();
    }

    public Rarity getNextRarity() {
        int nextOrdinal = this.ordinal() + 1;
        return (nextOrdinal < values().length) ? values()[nextOrdinal] : this;
    }
}
