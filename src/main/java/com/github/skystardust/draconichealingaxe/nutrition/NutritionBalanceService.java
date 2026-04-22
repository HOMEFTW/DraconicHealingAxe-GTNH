package com.github.skystardust.draconichealingaxe.nutrition;

import java.util.Map;

public class NutritionBalanceService {

    public static final float TARGET_NUTRITION = 50.0F;
    public static final float DEFAULT_STEP = 1.0F;
    private static final float MIN_NUTRITION = 0.0F;
    private static final float MAX_NUTRITION = 100.0F;

    private final float targetNutrition;
    private final float step;

    public NutritionBalanceService(float targetNutrition, float step) {
        if (step <= 0.0F) {
            throw new IllegalArgumentException("step must be positive");
        }
        this.targetNutrition = clamp(targetNutrition);
        this.step = step;
    }

    public static NutritionBalanceService healingAxeDefaults() {
        return new NutritionBalanceService(TARGET_NUTRITION, DEFAULT_STEP);
    }

    public <T> boolean balance(Map<T, Float> nutrients) {
        boolean changed = false;
        for (Map.Entry<T, Float> entry : nutrients.entrySet()) {
            float current = entry.getValue() == null ? targetNutrition : clamp(entry.getValue());
            float balanced = moveTowardTarget(current);
            if (Float.compare(entry.getValue() == null ? Float.NaN : entry.getValue(), balanced) != 0) {
                entry.setValue(balanced);
                changed = true;
            }
        }
        return changed;
    }

    private float moveTowardTarget(float current) {
        float difference = targetNutrition - current;
        if (Math.abs(difference) <= step) {
            return targetNutrition;
        }
        return clamp(current + Math.signum(difference) * step);
    }

    private static float clamp(float value) {
        if (value < MIN_NUTRITION) {
            return MIN_NUTRITION;
        }
        if (value > MAX_NUTRITION) {
            return MAX_NUTRITION;
        }
        return value;
    }
}
