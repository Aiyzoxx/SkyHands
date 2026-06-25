package net.skyhands.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GuiGraphics.class)
public class YACLScreenMixin {

    @ModifyVariable(method = "fill(IIIII)V", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private int skyhands$modifyFillColor(int color) {
        Screen screen = Minecraft.getInstance().screen;
        // Check if we are inside a config screen (YACL or Resourceful Config)
        if (screen != null && (screen.getClass().getName().contains("Config") || screen.getClass().getName().contains("YACL"))) {
            int alpha = (color >> 24) & 0xFF;
            if (alpha > 100) {
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = color & 0xFF;
                // If it's a dark color (gray/black background), typical for YACL background
                if (r < 60 && g < 60 && b < 60) {
                    // Make it fully transparent (alpha 0)
                    return color & 0x00FFFFFF;
                }
            }
        }
        return color;
    }

    @ModifyVariable(method = "fillGradient(IIIIII)V", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private int skyhands$modifyFillGradientColor1(int color) {
        return skyhands$modifyFillColor(color);
    }

    @ModifyVariable(method = "fillGradient(IIIIII)V", at = @At("HEAD"), argsOnly = true, ordinal = 1)
    private int skyhands$modifyFillGradientColor2(int color) {
        return skyhands$modifyFillColor(color);
    }
}
