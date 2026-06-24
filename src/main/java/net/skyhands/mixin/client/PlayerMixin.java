package net.skyhands.mixin.client;

import net.minecraft.world.entity.player.Player;
import net.skyhands.SkyHandsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "getItemSwapScale", at = @At("HEAD"), cancellable = true)
    private void skyhands$cancelReEquip(float partialTicks, CallbackInfoReturnable<Float> cir) {
        if (SkyHandsConfig.Offhand.cancelReEquip) {
            cir.setReturnValue(1.0f);
        }
    }
}
