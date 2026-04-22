package com.github.skystardust.draconichealingaxe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = DraconicHealingAxe.MODID,
    version = DraconicHealingAxe.VERSION,
    name = DraconicHealingAxe.MOD_NAME,
    acceptedMinecraftVersions = "[1.7.10]",
    dependencies = "required-after:DraconicEvolution;required-after:AppleCore;after:nutrition")
public class DraconicHealingAxe {

    public static final String MODID = "draconichealingaxe";
    public static final String MOD_NAME = "DraconicHealingAxe-GTNH";
    public static final String VERSION = Tags.VERSION;

    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(
        clientSide = "com.github.skystardust.draconichealingaxe.CommonProxy",
        serverSide = "com.github.skystardust.draconichealingaxe.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
