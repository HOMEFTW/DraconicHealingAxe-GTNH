package com.github.skystardust.draconichealingaxe.hunger;

import net.minecraft.entity.player.EntityPlayer;

import squeek.applecore.api.AppleCoreAPI;

public class AppleCorePlayerHungerState implements PlayerHungerState {

    private final EntityPlayer player;

    public AppleCorePlayerHungerState(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public int getFoodLevel() {
        return player.getFoodStats()
            .getFoodLevel();
    }

    @Override
    public void setFoodLevel(int foodLevel) {
        AppleCoreAPI.mutator.setHunger(player, foodLevel);
    }

    @Override
    public float getSaturationLevel() {
        return player.getFoodStats()
            .getSaturationLevel();
    }

    @Override
    public void setSaturationLevel(float saturationLevel) {
        AppleCoreAPI.mutator.setSaturation(player, saturationLevel);
    }
}
