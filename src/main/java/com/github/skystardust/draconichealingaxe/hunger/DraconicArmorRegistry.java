package com.github.skystardust.draconichealingaxe.hunger;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

public class DraconicArmorRegistry {

    private static final String DRACONIC_EVOLUTION_MODID = "DraconicEvolution";
    private static final String[] DRACONIC_ARMOR_ITEM_NAMES = { "draconicHelm", "draconicChest", "draconicLeggs",
        "draconicBoots" };

    private final Set<Item> draconicArmorItems = new HashSet<Item>();
    private boolean resolved;

    public boolean hasAnyDraconicArmor(ItemStack[] armorInventory) {
        if (armorInventory == null || armorInventory.length == 0) {
            return false;
        }

        resolveItems();
        for (ItemStack stack : armorInventory) {
            if (stack != null && draconicArmorItems.contains(stack.getItem())) {
                return true;
            }
        }
        return false;
    }

    private void resolveItems() {
        if (resolved) {
            return;
        }
        resolved = true;
        for (String itemName : DRACONIC_ARMOR_ITEM_NAMES) {
            Item item = GameRegistry.findItem(DRACONIC_EVOLUTION_MODID, itemName);
            if (item != null) {
                draconicArmorItems.add(item);
            }
        }
    }
}
