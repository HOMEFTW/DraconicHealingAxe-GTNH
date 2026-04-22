package com.github.skystardust.draconichealingaxe.hunger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HungerRefillServiceTest {

    @Test
    void refillsOneHungerAndOneSaturationAtHealingAxeCadence() {
        HungerRefillService service = HungerRefillService.healingAxeDefaults();
        MutableHungerState hunger = new MutableHungerState(17, 2.5F);

        assertTrue(service.shouldRefill(20));
        assertTrue(service.refill(hunger));

        assertEquals(18, hunger.foodLevel);
        assertEquals(3.5F, hunger.saturationLevel);
    }

    @Test
    void doesNotRefillBetweenCadenceTicks() {
        HungerRefillService service = HungerRefillService.healingAxeDefaults();

        assertFalse(service.shouldRefill(19));
    }

    @Test
    void capsFoodAndSaturationAtFullBars() {
        HungerRefillService service = HungerRefillService.healingAxeDefaults();
        MutableHungerState hunger = new MutableHungerState(20, 20.0F);

        assertFalse(service.refill(hunger));

        assertEquals(20, hunger.foodLevel);
        assertEquals(20.0F, hunger.saturationLevel);
    }

    @Test
    void neverRaisesSaturationAboveFoodLevel() {
        HungerRefillService service = HungerRefillService.healingAxeDefaults();
        MutableHungerState hunger = new MutableHungerState(4, 4.0F);

        service.refill(hunger);

        assertEquals(5, hunger.foodLevel);
        assertEquals(5.0F, hunger.saturationLevel);
    }

    @Test
    void rejectsInvalidCadence() {
        assertThrows(IllegalArgumentException.class, () -> new HungerRefillService(0, 1, 1.0F));
    }

    private static class MutableHungerState implements PlayerHungerState {

        private int foodLevel;
        private float saturationLevel;

        private MutableHungerState(int foodLevel, float saturationLevel) {
            this.foodLevel = foodLevel;
            this.saturationLevel = saturationLevel;
        }

        @Override
        public int getFoodLevel() {
            return foodLevel;
        }

        @Override
        public void setFoodLevel(int foodLevel) {
            this.foodLevel = foodLevel;
        }

        @Override
        public float getSaturationLevel() {
            return saturationLevel;
        }

        @Override
        public void setSaturationLevel(float saturationLevel) {
            this.saturationLevel = saturationLevel;
        }
    }
}
