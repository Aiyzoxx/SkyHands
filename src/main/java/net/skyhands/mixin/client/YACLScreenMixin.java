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
        if (screen != null && (screen.getClass().getName().contains("Config") || screen.getClass().getName().contains("YACL"))) {
            // Logic removed
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
