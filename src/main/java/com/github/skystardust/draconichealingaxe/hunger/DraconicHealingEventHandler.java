package com.github.skystardust.draconichealingaxe.hunger;

import com.github.skystardust.draconichealingaxe.nutrition.NutritionAdapter;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class DraconicHealingEventHandler {

    private final DraconicArmorRegistry armorRegistry;
    private final HungerRefillService refillService;
    private final NutritionAdapter nutritionAdapter;

    public DraconicHealingEventHandler(DraconicArmorRegistry armorRegistry, HungerRefillService refillService,
        NutritionAdapter nutritionAdapter) {
        this.armorRegistry = armorRegistry;
        this.refillService = refillService;
        this.nutritionAdapter = nutritionAdapter;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player == null || event.player.worldObj.isRemote) {
            return;
        }
        if (!refillService.shouldRefill(event.player.ticksExisted)) {
            return;
        }
        if (!armorRegistry.hasAnyDraconicArmor(event.player.inventory.armorInventory)) {
            return;
        }

        refillService.refill(new AppleCorePlayerHungerState(event.player));
        nutritionAdapter.balance(event.player);
    }
}
