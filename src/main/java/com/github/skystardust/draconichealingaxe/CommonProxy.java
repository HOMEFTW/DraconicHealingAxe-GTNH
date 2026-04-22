package com.github.skystardust.draconichealingaxe;

import net.minecraftforge.common.MinecraftForge;

import com.github.skystardust.draconichealingaxe.hunger.DraconicArmorRegistry;
import com.github.skystardust.draconichealingaxe.hunger.DraconicHealingEventHandler;
import com.github.skystardust.draconichealingaxe.hunger.HungerRefillService;
import com.github.skystardust.draconichealingaxe.nutrition.NutritionBalanceService;
import com.github.skystardust.draconichealingaxe.nutrition.ReflectiveNutritionAdapter;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {
        DraconicArmorRegistry armorRegistry = new DraconicArmorRegistry();
        HungerRefillService refillService = HungerRefillService.healingAxeDefaults();
        ReflectiveNutritionAdapter nutritionAdapter = new ReflectiveNutritionAdapter(
            NutritionBalanceService.healingAxeDefaults());
        DraconicHealingEventHandler handler = new DraconicHealingEventHandler(
            armorRegistry,
            refillService,
            nutritionAdapter);

        FMLCommonHandler.instance()
            .bus()
            .register(handler);
        MinecraftForge.EVENT_BUS.register(handler);
    }
}
