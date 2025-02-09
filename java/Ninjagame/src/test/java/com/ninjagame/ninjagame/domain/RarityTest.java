package com.ninjagame.ninjagame.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RarityTest {

    @Test
    void testSafeRarityConversion_ValidInput() {
        assertEquals(Rarity.EPIC, Rarity.safeRarityConversion("EPIC"));
        assertEquals(Rarity.UNCOMMON, Rarity.safeRarityConversion("uncommon"));
        assertEquals(Rarity.MYTHIC, Rarity.safeRarityConversion("MyThIc")); // Case insensitive
    }

    @Test
    void testSafeRarityConversion_InvalidInput() {
        assertEquals(Rarity.COMMON, Rarity.safeRarityConversion("INVALID_RARITY"));
        assertEquals(Rarity.COMMON, Rarity.safeRarityConversion(" "));
    }

    @Test
    void testSafeRarityConversion_NullInput() {
        assertEquals(Rarity.COMMON, Rarity.safeRarityConversion(null));
    }

    @Test
    void testToPrettyString() {
        assertEquals("Common", Rarity.COMMON.toPrettyString());
        assertEquals("Epic", Rarity.EPIC.toPrettyString());
        assertEquals("Legendary", Rarity.LEGENDARY.toPrettyString());
    }

    @Test
    void testIsHigherThan() {
        assertTrue(Rarity.RARE.isHigherThan(Rarity.UNCOMMON));
        assertTrue(Rarity.LEGENDARY.isHigherThan(Rarity.EPIC));
        assertFalse(Rarity.COMMON.isHigherThan(Rarity.MYTHIC));
    }

    @Test
    void testGetNextRarity() {
        assertEquals(Rarity.UNCOMMON, Rarity.COMMON.getNextRarity());
        assertEquals(Rarity.RARE, Rarity.UNCOMMON.getNextRarity());
        assertEquals(Rarity.EPIC, Rarity.RARE.getNextRarity());
        assertEquals(Rarity.MYTHIC, Rarity.LEGENDARY.getNextRarity());
        assertEquals(Rarity.UNIQUE, Rarity.EXOTIC.getNextRarity());
        assertEquals(Rarity.UNIQUE, Rarity.UNIQUE.getNextRarity()); // Should stay UNIQUE
    }
}
