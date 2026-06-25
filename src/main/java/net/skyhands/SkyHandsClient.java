package net.skyhands;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.minecraft.client.Minecraft;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import com.teamresourceful.resourcefulconfig.api.client.ResourcefulConfigScreen;

public class SkyHandsClient implements ClientModInitializer {
    
    public static final Configurator CONFIGURATOR = new Configurator("skyhands");

    @Override
    public void onInitializeClient() {
        CONFIGURATOR.register(SkyHandsConfig.class);

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("skyhands").executes(context -> {
                Minecraft.getInstance().execute(() -> {
                    Minecraft.getInstance().setScreen(SkyHandsConfigScreen.create(null));
                });
                return 1;
            }));
        });
    }
}
