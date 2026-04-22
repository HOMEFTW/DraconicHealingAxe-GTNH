package com.github.skystardust.draconichealingaxe.nutrition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class NutritionBalanceServiceTest {

    @Test
    void movesEachNutrientOnePointTowardFifty() {
        NutritionBalanceService service = NutritionBalanceService.healingAxeDefaults();
        Map<String, Float> nutrients = new LinkedHashMap<String, Float>();
        nutrients.put("fruit", 30.0F);
        nutrients.put("grain", 70.0F);
        nutrients.put("protein", 50.0F);

        assertTrue(service.balance(nutrients));

        assertEquals(31.0F, nutrients.get("fruit"));
        assertEquals(69.0F, nutrients.get("grain"));
        assertEquals(50.0F, nutrients.get("protein"));
    }

    @Test
    void snapsToFiftyWhenWithinOneStep() {
        NutritionBalanceService service = NutritionBalanceService.healingAxeDefaults();
        Map<String, Float> nutrients = new LinkedHashMap<String, Float>();
        nutrients.put("fruit", 49.25F);
        nutrients.put("grain", 50.75F);

        assertTrue(service.balance(nutrients));

        assertEquals(50.0F, nutrients.get("fruit"));
        assertEquals(50.0F, nutrients.get("grain"));
    }

    @Test
    void reportsNoChangeWhenAllNutrientsAreBalanced() {
        NutritionBalanceService service = NutritionBalanceService.healingAxeDefaults();
        Map<String, Float> nutrients = new LinkedHashMap<String, Float>();
        nutrients.put("fruit", 50.0F);
        nutrients.put("grain", 50.0F);

        assertFalse(service.balance(nutrients));

        assertEquals(50.0F, nutrients.get("fruit"));
        assertEquals(50.0F, nutrients.get("grain"));
    }

    @Test
    void clampsOutOfRangeValuesWhileMovingTowardFifty() {
        NutritionBalanceService service = NutritionBalanceService.healingAxeDefaults();
        Map<String, Float> nutrients = new LinkedHashMap<String, Float>();
        nutrients.put("fruit", -10.0F);
        nutrients.put("grain", 120.0F);

        assertTrue(service.balance(nutrients));

        assertEquals(1.0F, nutrients.get("fruit"));
        assertEquals(99.0F, nutrients.get("grain"));
    }
}
