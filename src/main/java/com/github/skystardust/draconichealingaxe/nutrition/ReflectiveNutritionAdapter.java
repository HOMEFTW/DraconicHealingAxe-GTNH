package com.github.skystardust.draconichealingaxe.nutrition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;

import com.github.skystardust.draconichealingaxe.DraconicHealingAxe;

import cpw.mods.fml.common.Loader;

public class ReflectiveNutritionAdapter implements NutritionAdapter {

    private static final String NUTRITION_MODID = "nutrition";
    private static final String PLAYER_DATA_HANDLER_CLASS = "ca.wescook.nutrition.data.PlayerDataHandler";
    private static final String NUTRIENT_MANAGER_CLASS = "ca.wescook.nutrition.data.NutrientManager";
    private static final String NUTRIENT_CLASS = "ca.wescook.nutrition.nutrients.Nutrient";

    private final NutritionBalanceService balanceService;
    private boolean initialized;
    private boolean available = true;
    private Method getForPlayer;
    private Method setForPlayer;
    private Method managerGet;

    public ReflectiveNutritionAdapter(NutritionBalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Override
    public void balance(EntityPlayer player) {
        if (!Loader.isModLoaded(NUTRITION_MODID) || !available || player == null || player.worldObj.isRemote) {
            return;
        }
        if (!initialize()) {
            return;
        }

        try {
            Object manager = getForPlayer.invoke(null, player);
            @SuppressWarnings("unchecked")
            Map<Object, Float> nutrients = (Map<Object, Float>) managerGet.invoke(manager);
            if (balanceService.balance(nutrients)) {
                setForPlayer.invoke(null, player, manager, true);
            }
        } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
            disable(e);
        }
    }

    private boolean initialize() {
        if (initialized) {
            return available;
        }
        initialized = true;
        try {
            Class<?> playerDataHandlerClass = Class.forName(PLAYER_DATA_HANDLER_CLASS);
            Class<?> managerClass = Class.forName(NUTRIENT_MANAGER_CLASS);
            Class.forName(NUTRIENT_CLASS);

            getForPlayer = playerDataHandlerClass.getMethod("getForPlayer", EntityPlayer.class);
            setForPlayer = playerDataHandlerClass
                .getMethod("setForPlayer", EntityPlayer.class, managerClass, boolean.class);
            managerGet = managerClass.getMethod("get");
        } catch (ReflectiveOperationException | LinkageError e) {
            disable(e);
        }
        return available;
    }

    private void disable(Throwable cause) {
        available = false;
        DraconicHealingAxe.LOG.warn("disabling Nutrition compatibility because Nutrition API lookup failed", cause);
    }
}
