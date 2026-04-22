package com.github.skystardust.draconichealingaxe.hunger;

public class HungerRefillService {

    public static final int MAX_FOOD_LEVEL = 20;
    public static final float MAX_SATURATION_LEVEL = 20.0F;
    public static final int DEFAULT_TICK_INTERVAL = 10;
    public static final int DEFAULT_FOOD_PER_STEP = 1;
    public static final float DEFAULT_SATURATION_PER_STEP = 1.0F;

    private final int tickInterval;
    private final int foodPerStep;
    private final float saturationPerStep;

    public HungerRefillService(int tickInterval, int foodPerStep, float saturationPerStep) {
        if (tickInterval <= 0) {
            throw new IllegalArgumentException("tickInterval must be positive");
        }
        if (foodPerStep <= 0) {
            throw new IllegalArgumentException("foodPerStep must be positive");
        }
        if (saturationPerStep <= 0.0F) {
            throw new IllegalArgumentException("saturationPerStep must be positive");
        }
        this.tickInterval = tickInterval;
        this.foodPerStep = foodPerStep;
        this.saturationPerStep = saturationPerStep;
    }

    public static HungerRefillService healingAxeDefaults() {
        return new HungerRefillService(DEFAULT_TICK_INTERVAL, DEFAULT_FOOD_PER_STEP, DEFAULT_SATURATION_PER_STEP);
    }

    public boolean shouldRefill(int ticksExisted) {
        return ticksExisted % tickInterval == 0;
    }

    public boolean refill(PlayerHungerState hungerState) {
        int currentFood = hungerState.getFoodLevel();
        float currentSaturation = hungerState.getSaturationLevel();
        int nextFood = Math.min(MAX_FOOD_LEVEL, currentFood + foodPerStep);
        float nextSaturation = Math.min(MAX_SATURATION_LEVEL, currentSaturation + saturationPerStep);
        nextSaturation = Math.min(nextSaturation, nextFood);

        if ((nextFood == currentFood) && (Float.compare(nextSaturation, currentSaturation) == 0)) {
            return false;
        }

        hungerState.setFoodLevel(nextFood);
        hungerState.setSaturationLevel(nextSaturation);
        return true;
    }
}
