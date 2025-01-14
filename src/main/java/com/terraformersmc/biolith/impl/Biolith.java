package com.terraformersmc.biolith.impl;

import com.terraformersmc.biolith.impl.biome.BiomeCoordinator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Biolith implements ModInitializer {
    public static final String MOD_ID = "biolith";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final boolean COMPAT_DATAGEN = System.getProperty("fabric-api.datagen") != null;
    public static final boolean COMPAT_TERRABLENDER = FabricLoader.getInstance().isModLoaded("terrablender");

    @Override
    public void onInitialize() {
        LOGGER.info("Biolith is initializing...");

        // TODO: Is there a better way to do this?
        if (COMPAT_DATAGEN) {
            LOGGER.info("Suppressing worldgen during datagen...");
        } else {
            // Watch for server events so we can maintain our status data.
            ServerLifecycleEvents.SERVER_STARTING.register(BiomeCoordinator::handleServerStarting);
            ServerLifecycleEvents.SERVER_STOPPED.register(BiomeCoordinator::handleServerStopped);

            if (COMPAT_TERRABLENDER) {
                LOGGER.info("Enabling Biolith's TerraBlender compatibility layer.");
            }
        }
    }
}
