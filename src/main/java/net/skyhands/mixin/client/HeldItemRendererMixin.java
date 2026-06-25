package net.skyhands.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.skyhands.SkyHandsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.Mth;
import org.joml.Quaternionfc;

@Mixin(value={ItemInHandRenderer.class})
public abstract class HeldItemRendererMixin {

    @Shadow protected abstract void applyItemArmTransform(PoseStack poseStack, HumanoidArm arm, float equipProgress);
    @Shadow protected abstract void applyItemArmAttackTransform(PoseStack poseStack, HumanoidArm arm, float swingProgress);

    @Unique
    private static void SkyHands$apply(PoseStack poseStack, boolean enabled, boolean isOffhand) {
        if (!enabled) {
            return;
        }

        float x = (isOffhand ? SkyHandsConfig.Offhand.offX : SkyHandsConfig.Position.xOffset) / 100.0f;
        float y = (isOffhand ? SkyHandsConfig.Offhand.offY : SkyHandsConfig.Position.yOffset) / 100.0f;
        float z = (isOffhand ? SkyHandsConfig.Offhand.offZ : SkyHandsConfig.Position.zOffset) / 100.0f;
        if (x != 0.0f || y != 0.0f || z != 0.0f) {
            poseStack.translate((double)x, (double)y, (double)z);
        }

        float rx = SkyHandsConfig.Position.rotX;
        float ry = SkyHandsConfig.Position.rotY;
        float rz = SkyHandsConfig.Position.rotZ;
        if (rx != 0.0f) poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(rx));
        if (ry != 0.0f) poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(ry));
        if (rz != 0.0f) poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(rz));

        float s = isOffhand ? SkyHandsConfig.Offhand.offScale : SkyHandsConfig.Position.scale;
        if (s != 1.0f) {
            poseStack.scale(s, s, s);
        }
    }

    @Inject(method={"renderItem"}, at={@At(value="HEAD")})
    private void SkyHands$preRenderItem(LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, @Coerce Object buffer, int packedLight, CallbackInfo ci) {
        poseStack.pushPose();
        boolean isOffhand = (displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND);
        HeldItemRendererMixin.SkyHands$apply(poseStack, SkyHandsConfig.Offhand.affectItem, isOffhand);
    }

    @Inject(method={"renderItem"}, at={@At(value="RETURN")})
    private void SkyHands$postRenderItem(LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, @Coerce Object buffer, int packedLight, CallbackInfo ci) {
        poseStack.popPose();
    }

    @Inject(method={"renderPlayerArm"}, at={@At(value="HEAD")})
    private void SkyHands$preRenderArm(PoseStack poseStack, @Coerce Object buffer, int packedLight, float equipProgress, float swingProgress, HumanoidArm arm, CallbackInfo ci) {
        poseStack.pushPose();
        boolean isOffhand = false;
        if (Minecraft.getInstance().player != null) {
            isOffhand = arm != Minecraft.getInstance().player.getMainArm();
        }
        HeldItemRendererMixin.SkyHands$apply(poseStack, SkyHandsConfig.Offhand.affectArm, isOffhand);
    }

    @Inject(method={"renderPlayerArm"}, at={@At(value="RETURN")})
    private void SkyHands$postRenderArm(PoseStack poseStack, @Coerce Object buffer, int packedLight, float equipProgress, float swingProgress, HumanoidArm arm, CallbackInfo ci) {
        poseStack.popPose();
    }

    @Inject(method="applyItemArmTransform", at=@At("HEAD"), cancellable=true)
    private void dulkir$onApplyEquipOffset(PoseStack poseStack, HumanoidArm humanoidArm, float f, CallbackInfo ci) {
        if (SkyHandsConfig.Offhand.cancelReEquip) {
            int i = humanoidArm == HumanoidArm.RIGHT ? 1 : -1;
            poseStack.translate((float)i * 0.56f, -0.52f, -0.72f);
            ci.cancel();
        }
    }

    @Inject(method="applyEatTransform", at=@At(value="INVOKE", target="Ljava/lang/Math;pow(DD)D", shift=At.Shift.BEFORE), cancellable=true)
    private void onDrink(PoseStack poseStack, float f, HumanoidArm humanoidArm, ItemStack itemStack, net.minecraft.world.entity.player.Player player, CallbackInfo ci) {
        if (SkyHandsConfig.Offhand.rotationlessDrink) {
            ci.cancel();
        }
    }

    @Inject(method={"swingArm"}, at={@At(value="HEAD")}, cancellable=true)
    private void skyhands$overrideSwingDrift(float attackProgress, PoseStack poseStack, int handSide, HumanoidArm arm, CallbackInfo ci) {
        if (SkyHandsConfig.Animations.swingDriftX == 0 && SkyHandsConfig.Animations.swingDriftY == 0 && SkyHandsConfig.Animations.swingDriftZ == 0) return;
        ci.cancel();
        float sqrtAttack = Mth.sqrt(attackProgress);
        float driftX = (SkyHandsConfig.Animations.swingDriftX / 100.0f) * Mth.sin(sqrtAttack * (float)Math.PI);
        float driftY = (SkyHandsConfig.Animations.swingDriftY / 100.0f) * Mth.sin(sqrtAttack * ((float)Math.PI * 2));
        float driftZ = (SkyHandsConfig.Animations.swingDriftZ / 100.0f) * Mth.sin(attackProgress * (float)Math.PI);
        poseStack.translate(handSide * driftX, driftY, driftZ);
        this.applyItemArmAttackTransform(poseStack, arm, attackProgress);
    }

    @Inject(method={"applyItemArmAttackTransform"}, at={@At(value="HEAD")}, cancellable=true)
    private void skyhands$overrideSwingArc(PoseStack poseStack, HumanoidArm arm, float attackProgress, CallbackInfo ci) {
        if (SkyHandsConfig.Animations.swingArcX == 0 && SkyHandsConfig.Animations.swingArcY == 0 && SkyHandsConfig.Animations.swingArcZ == 0 && !SkyHandsConfig.Offhand.swingCounterRotation) return;
        ci.cancel();
        int armSideSign = arm == HumanoidArm.RIGHT ? 1 : -1;
        float lateSwingCurve = Mth.sin(attackProgress * attackProgress * (float)Math.PI);
        float midSwingCurve = Mth.sin(Mth.sqrt(attackProgress) * (float)Math.PI);
        
        float preY = SkyHandsConfig.Offhand.swingCounterRotation ? 45.0f : 0.0f;
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees((float)armSideSign * (preY + lateSwingCurve * SkyHandsConfig.Animations.swingArcY)));
        poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees((float)armSideSign * midSwingCurve * SkyHandsConfig.Animations.swingArcZ));
        poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(midSwingCurve * SkyHandsConfig.Animations.swingArcX));
        if (SkyHandsConfig.Offhand.swingCounterRotation) {
            poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees((float)armSideSign * -preY));
        }
    }

    @Inject(method={"renderArmWithItem"}, at={@At(value="HEAD")}, cancellable=true)
    private void skyhands$applySwordBlockPose(AbstractClientPlayer player, float tickDelta, float pitch, InteractionHand hand, float swingProgress, ItemStack heldItem, float equipProgress, PoseStack poseStack, @Coerce Object collector, int packedLight, CallbackInfo ci) {
        if (!SkyHandsConfig.Animations.blockHitting || hand != InteractionHand.MAIN_HAND || !heldItem.is(net.minecraft.tags.ItemTags.SWORDS)) return;
        if (!Minecraft.getInstance().options.keyUse.isDown()) return;
        
        ci.cancel();
        poseStack.pushPose();
        HumanoidArm arm = player.getMainArm();
        int side = arm == HumanoidArm.RIGHT ? 1 : -1;
        this.applyItemArmTransform(poseStack, arm, equipProgress);
        poseStack.translate((float)side * -0.14142136f, 0.08f, 0.14142136f);
        poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(-102.25f));
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees((float)side * 13.365f));
        poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees((float)side * 78.05f));
        
        ItemDisplayContext ctx = arm == HumanoidArm.RIGHT ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
        ((ItemInHandRenderer)(Object)this).renderItem((LivingEntity)player, heldItem, ctx, poseStack, (net.minecraft.client.renderer.SubmitNodeCollector)collector, packedLight);
        
        poseStack.popPose();
    }

    @org.spongepowered.asm.mixin.injection.Redirect(method="tick()V", at=@At(value="INVOKE", target="Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"))
    private float skyhands$suppressSwingBobbing(net.minecraft.client.player.LocalPlayer player, float partialTick) {
        if (SkyHandsConfig.Animations.suppressBobbing) {
            return 1.0f;
        }
        return player.getAttackStrengthScale(partialTick);
    }
}
