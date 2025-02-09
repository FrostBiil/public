package com.ninjagame.ninjagame.domain.reward;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RewardTypeTest {

    @Test
    void testRewardTypeValues() {
        assertEquals(4, RewardType.values().length);
        assertEquals(RewardType.COINS, RewardType.valueOf("COINS"));
        assertEquals(RewardType.EXP, RewardType.valueOf("EXP"));
        assertEquals(RewardType.WEAPON, RewardType.valueOf("WEAPON"));
        assertEquals(RewardType.NINJA, RewardType.valueOf("NINJA"));
    }

    @Test
    void testInvalidRewardType() {
        assertThrows(IllegalArgumentException.class, () -> RewardType.valueOf("INVALID_TYPE"));
    }
}
