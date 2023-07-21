package com.impact.lib.api.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomsTest {

    @Test
    @DisplayName("Get random")
    void randomInt() {
        Range<Integer> range = new ImmutableNumberRange<>(5, 10);
        int random = Randoms.inRange(range);
        assertTrue(random >= 5 && random <= 10);
    }

}