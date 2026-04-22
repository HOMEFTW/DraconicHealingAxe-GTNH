package com.github.skystardust.draconichealingaxe.hunger;

public interface PlayerHungerState {

    int getFoodLevel();

    void setFoodLevel(int foodLevel);

    float getSaturationLevel();

    void setSaturationLevel(float saturationLevel);
}
