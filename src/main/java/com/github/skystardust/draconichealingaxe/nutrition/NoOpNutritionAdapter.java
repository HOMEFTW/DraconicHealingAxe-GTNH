package com.github.skystardust.draconichealingaxe.nutrition;

import net.minecraft.entity.player.EntityPlayer;

public class NoOpNutritionAdapter implements NutritionAdapter {

    @Override
    public void balance(EntityPlayer player) {}
}
