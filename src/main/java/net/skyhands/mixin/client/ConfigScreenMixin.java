package net.skyhands.mixin.client;

import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConfigScreen.class)
public class ConfigScreenMixin {
    @Inject(method = "renderBackground", at = @At("HEAD"), cancellable = true)
    private void skyhands$cancelBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        ci.cancel();
    }
}
