package com.github.skystardust.draconichealingaxe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DraconicHealingAxeMetadataTest {

    @Test
    void exposesStableModMetadata() {
        assertEquals("draconichealingaxe", DraconicHealingAxe.MODID);
        assertEquals("DraconicHealingAxe-GTNH", DraconicHealingAxe.MOD_NAME);
    }
}
