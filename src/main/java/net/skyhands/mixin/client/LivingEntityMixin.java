package net.skyhands.mixin.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.skyhands.SkyHandsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "getCurrentSwingDuration", at = @At("RETURN"), cancellable = true)
    private void skyhands$modifySwingDuration(CallbackInfoReturnable<Integer> cir) {
        if (!((Object) this instanceof LocalPlayer)) return;
        
        float speed = SkyHandsConfig.Animations.swingSpeed;
        boolean constant = SkyHandsConfig.Animations.constantSwingSpeed;

        if (speed == 1.0f && !constant) {
            return;
        }

        int originalDuration = cir.getReturnValue();
        int baseDuration = constant ? 6 : originalDuration;
        int customDuration = Math.max(1, Math.round((float) baseDuration / speed));
        cir.setReturnValue(customDuration);
    }
}
