package net.skyhands;

import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.Minecraft;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SkyHandsConfigScreen {
    public static Screen create(Screen parent) {
        final String[] presetInput = new String[]{""};
        return YetAnotherConfigLib.createBuilder()
            .title(Component.literal("SkyHands"))
            .save(() -> {
                SkyHandsClient.CONFIGURATOR.saveConfig(SkyHandsConfig.class);
            })
            .category(ConfigCategory.createBuilder()
                .name(Component.literal("Position"))
                .group(OptionGroup.createBuilder().name(Component.literal("Position Options"))
                    .option(Option.<Float>createBuilder().name(Component.literal("Scale"))
                        .description(OptionDescription.of(Component.literal("Changes the overall size of the item in your main hand.")))
                        .binding(1.0f, () -> SkyHandsConfig.Position.scale, v -> SkyHandsConfig.Position.scale = v)
                        .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.05f, 1.5f).step(0.01f).formatValue(v -> Component.literal(String.format(java.util.Locale.US, "%.2f", v)))).instant(true).build())
                    .option(Option.<Integer>createBuilder().name(Component.literal("X Offset"))
                        .description(OptionDescription.of(Component.literal("Moves the item left or right.")))
                        .binding(0, () -> SkyHandsConfig.Position.xOffset, v -> SkyHandsConfig.Position.xOffset = v)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(-150, 150).step(1)).instant(true).build())
                    .option(Option.<Integer>createBuilder().name(Component.literal("Y Offset"))
                        .description(OptionDescription.of(Component.literal("Moves the item up or down.")))
                        .binding(0, () -> SkyHandsConfig.Position.yOffset, v -> SkyHandsConfig.Position.yOffset = v)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(-150, 150).step(1)).instant(true).build())
                    .option(Option.<Integer>createBuilder().name(Component.literal("Z Offset"))
                        .description(OptionDescription.of(Component.literal("Moves the item closer or further from the camera.")))
                        .binding(0, () -> SkyHandsConfig.Position.zOffset, v -> SkyHandsConfig.Position.zOffset = v)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(-150, 150).step(1)).instant(true).build())
                    .build())
                .group(OptionGroup.createBuilder().name(Component.literal("Rotation Options"))
                    .option(Option.<Float>createBuilder().name(Component.literal("Rot X"))
                        .description(OptionDescription.of(Component.literal("Rotates the item up or down (pitch).")))
                        .binding(0.0f, () -> SkyHandsConfig.Position.rotX, v -> SkyHandsConfig.Position.rotX = v)
                        .controller(opt -> FloatSliderControllerBuilder.create(opt).range(-180.0f, 180.0f).step(1.0f)).instant(true).build())
                    .option(Option.<Float>createBuilder().name(Component.literal("Rot Y"))
                        .description(OptionDescription.of(Component.literal("Rotates the item left or right (yaw).")))
                        .binding(0.0f, () -> SkyHandsConfig.Position.rotY, v -> SkyHandsConfig.Position.rotY = v)
                        .controller(opt -> FloatSliderControllerBuilder.create(opt).range(-180.0f, 180.0f).step(1.0f)).instant(true).build())
                    .option(Option.<Float>createBuilder().name(Component.literal("Rot Z"))
                        .description(OptionDescription.of(Component.literal("Tilts the item side to side (roll).")))
                        .binding(0.0f, () -> SkyHandsConfig.Position.rotZ, v -> SkyHandsConfig.Position.rotZ = v)
                        .controller(opt -> FloatSliderControllerBuilder.create(opt).range(-180.0f, 180.0f).step(1.0f)).instant(true).build())
                    .build())
                .build())
            .category(ConfigCategory.createBuilder()
                .name(Component.literal("Animations"))
                .group(OptionGroup.createBuilder().name(Component.literal("Animation Settings"))
                    .option(Option.<Float>createBuilder().name(Component.literal("Swing Speed"))
                        .description(OptionDescription.of(Component.literal("Adjusts how fast your swing animation plays.")))
                        .binding(1.0f, () -> SkyHandsConfig.Animations.swingSpeed, v -> SkyHandsConfig.Animations.swingSpeed = v)
                        .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.1f, 3.0f).step(0.01f).formatValue(v -> Component.literal(String.format(java.util.Locale.US, "%.2f", v)))).instant(true).build())
                    .option(Option.<Boolean>createBuilder().name(Component.literal("Constant Speed"))
                        .description(OptionDescription.of(Component.literal("Makes the swing animation speed constant regardless of haste.")))
                        .binding(false, () -> SkyHandsConfig.Animations.constantSwingSpeed, v -> SkyHandsConfig.Animations.constantSwingSpeed = v)
                        .controller(TickBoxControllerBuilder::create).instant(true).build())
                    .option(Option.<Boolean>createBuilder().name(Component.literal("Block Hitting (1.8)"))
                        .description(OptionDescription.of(Component.literal("Restores the classic 1.8 style block-hitting animation.")))
                        .binding(false, () -> SkyHandsConfig.Animations.blockHitting, v -> SkyHandsConfig.Animations.blockHitting = v)
                        .controller(TickBoxControllerBuilder::create).instant(true).build())
                    .option(Option.<Boolean>createBuilder().name(Component.literal("Suppress Bobbing"))
                        .description(OptionDescription.of(Component.literal("Disables the view bobbing effect when walking.")))
                        .binding(false, () -> SkyHandsConfig.Animations.suppressBobbing, v -> SkyHandsConfig.Animations.suppressBobbing = v)
                        .controller(TickBoxControllerBuilder::create).instant(true).build())
                    .build())
                .group(OptionGroup.createBuilder().name(Component.literal("Swing Drift & Arc"))
                    .option(Option.<Integer>createBuilder().name(Component.literal("Drift X"))
                        .description(OptionDescription.of(Component.literal("Changes the X position during the swing animation.")))
                        .binding(0, () -> SkyHandsConfig.Animations.swingDriftX, v -> SkyHandsConfig.Animations.swingDriftX = v)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(-150, 150).step(1)).instant(true).build())
                    .option(Option.<Integer>createBuilder().name(Component.literal("Drift Y"))
                        .description(OptionDescription.of(Component.literal("Changes the Y position during the swing animation.")))
                        .binding(0, () -> SkyHandsConfig.Animations.swingDriftY, v -> SkyHandsConfig.Animations.swingDriftY = v)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(-150, 150).step(1)).instant(true).build())
                    .option(Option.<Integer>createBuilder().name(Component.literal("Drift Z"))
                        .description(OptionDescription.of(Component.literal("Changes the Z position during the swing animation.")))
                        .binding(0, () -> SkyHandsConfig.Animations.swingDriftZ, v -> SkyHandsConfig.Animations.swingDriftZ = v)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(-150, 150).step(1)).instant(true).build())
                    .option(Option.<Integer>createBuilder().name(Component.literal("Arc X"))
                        .description(OptionDescription.of(Component.literal("Adjusts the X rotation arc of the swing.")))
                        .binding(0, () -> SkyHandsConfig.Animations.swingArcX, v -> SkyHandsConfig.Animations.swingArcX = v)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(-150, 150).step(1)).instant(true).build())
                    .option(Option.<Integer>createBuilder().name(Component.literal("Arc Y"))
                        .description(OptionDescription.of(Component.literal("Adjusts the Y rotation arc of the swing.")))
                        .binding(0, () -> SkyHandsConfig.Animations.swingArcY, v -> SkyHandsConfig.Animations.swingArcY = v)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(-150, 150).step(1)).instant(true).build())
                    .option(Option.<Integer>createBuilder().name(Component.literal("Arc Z"))
                        .description(OptionDescription.of(Component.literal("Adjusts the Z rotation arc of the swing.")))
                        .binding(0, () -> SkyHandsConfig.Animations.swingArcZ, v -> SkyHandsConfig.Animations.swingArcZ = v)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(-150, 150).step(1)).instant(true).build())
                    .build())
                .build())
            .category(ConfigCategory.createBuilder()
                .name(Component.literal("Offhand"))
                .group(OptionGroup.createBuilder().name(Component.literal("Offhand Position"))
                    .option(Option.<Float>createBuilder().name(Component.literal("Offhand Scale"))
                        .description(OptionDescription.of(Component.literal("Changes the overall size of the item in your offhand.")))
                        .binding(0.6f, () -> SkyHandsConfig.Offhand.offScale, v -> SkyHandsConfig.Offhand.offScale = v)
                        .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.05f, 1.5f).step(0.01f).formatValue(v -> Component.literal(String.format(java.util.Locale.US, "%.2f", v)))).instant(true).build())
                    .option(Option.<Integer>createBuilder().name(Component.literal("Off X"))
                        .description(OptionDescription.of(Component.literal("Moves the offhand item left or right.")))
                        .binding(0, () -> SkyHandsConfig.Offhand.offX, v -> SkyHandsConfig.Offhand.offX = v)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(-150, 150).step(1)).instant(true).build())
                    .option(Option.<Integer>createBuilder().name(Component.literal("Off Y"))
                        .description(OptionDescription.of(Component.literal("Moves the offhand item up or down.")))
                        .binding(0, () -> SkyHandsConfig.Offhand.offY, v -> SkyHandsConfig.Offhand.offY = v)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(-150, 150).step(1)).instant(true).build())
                    .option(Option.<Integer>createBuilder().name(Component.literal("Off Z"))
                        .description(OptionDescription.of(Component.literal("Moves the offhand item closer or further from the camera.")))
                        .binding(0, () -> SkyHandsConfig.Offhand.offZ, v -> SkyHandsConfig.Offhand.offZ = v)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(-150, 150).step(1)).instant(true).build())
                    .build())
                .group(OptionGroup.createBuilder().name(Component.literal("Extras"))
                    .option(Option.<Boolean>createBuilder().name(Component.literal("Counter Rot"))
                        .description(OptionDescription.of(Component.literal("Expert option to re-center the default swing axis when using Arc Y/Z.")))
                        .binding(false, () -> SkyHandsConfig.Offhand.swingCounterRotation, v -> SkyHandsConfig.Offhand.swingCounterRotation = v)
                        .controller(TickBoxControllerBuilder::create).instant(true).build())
                    .option(Option.<Boolean>createBuilder().name(Component.literal("Cancel ReEquip"))
                        .description(OptionDescription.of(Component.literal("Prevents the sword from going down and up when switching slots.")))
                        .binding(false, () -> SkyHandsConfig.Offhand.cancelReEquip, v -> SkyHandsConfig.Offhand.cancelReEquip = v)
                        .controller(TickBoxControllerBuilder::create).instant(true).build())
                    .option(Option.<Boolean>createBuilder().name(Component.literal("Affect Item"))
                        .description(OptionDescription.of(Component.literal("Applies the position settings to items you are holding.")))
                        .binding(true, () -> SkyHandsConfig.Offhand.affectItem, v -> SkyHandsConfig.Offhand.affectItem = v)
                        .controller(TickBoxControllerBuilder::create).instant(true).build())
                    .option(Option.<Boolean>createBuilder().name(Component.literal("Affect Arm"))
                        .description(OptionDescription.of(Component.literal("Applies the position settings to your empty hand.")))
                        .binding(true, () -> SkyHandsConfig.Offhand.affectArm, v -> SkyHandsConfig.Offhand.affectArm = v)
                        .controller(TickBoxControllerBuilder::create).instant(true).build())
                    .option(Option.<Boolean>createBuilder().name(Component.literal("Rotless Drink"))
                        .description(OptionDescription.of(Component.literal("Prevents potions from rotating towards your screen when drinking.")))
                        .binding(false, () -> SkyHandsConfig.Offhand.rotationlessDrink, v -> SkyHandsConfig.Offhand.rotationlessDrink = v)
                        .controller(TickBoxControllerBuilder::create).instant(true).build())
                    .build())
                .build())
            .category(((java.util.function.Supplier<ConfigCategory>) () -> {
                ConfigCategory.Builder presetsCategory = ConfigCategory.createBuilder()
                    .name(Component.literal("Presets"))
                    .group(OptionGroup.createBuilder().name(Component.literal("Create New Preset"))
                        .option(Option.<String>createBuilder().name(Component.literal("Preset Name"))
                            .description(OptionDescription.of(Component.literal("Type a name for your new preset here.")))
                            .binding("", () -> presetInput[0], s -> presetInput[0] = s)
                            .controller(StringControllerBuilder::create).instant(true).build())
                        .option(ButtonOption.createBuilder().name(Component.literal("Save Current As Preset"))
                            .text(Component.literal("Create"))
                            .description(OptionDescription.of(Component.literal("Saves your current configuration to a preset with the name above.")))
                            .action((screen, opt) -> {
                                if (!presetInput[0].isEmpty()) {
                                    SkyHandsPresets.savePreset(presetInput[0]);
                                    presetInput[0] = "";
                                    Minecraft.getInstance().setScreen(SkyHandsConfigScreen.create(parent));
                                }
                            }).build())
                        .build());
                List<String> presets = SkyHandsPresets.getPresetNames();
                if (presets.isEmpty()) {
                    presetsCategory.group(OptionGroup.createBuilder().name(Component.literal("Saved Presets"))
                        .option(ButtonOption.createBuilder().name(Component.literal("No presets saved yet.")).action((s, o) -> {}).build())
                        .build());
                } else {
                    for (String p : presets) {
                        presetsCategory.group(OptionGroup.createBuilder().name(Component.literal(p))
                            .option(ButtonOption.createBuilder().name(Component.literal("Load: " + p))
                                .text(Component.literal("Load"))
                                .description(OptionDescription.of(Component.literal("Load this preset.")))
                                .action((screen, opt) -> {
                                    SkyHandsPresets.loadPreset(p);
                                    Minecraft.getInstance().setScreen(SkyHandsConfigScreen.create(parent));
                                }).build())
                            .option(ButtonOption.createBuilder().name(Component.literal("Delete: " + p))
                                .text(Component.literal("Delete"))
                                .description(OptionDescription.of(Component.literal("Delete this preset permanently.")))
                                .action((screen, opt) -> {
                                    SkyHandsPresets.deletePreset(p);
                                    Minecraft.getInstance().setScreen(SkyHandsConfigScreen.create(parent));
                                }).build())
                            .build());
                    }
                }
                return presetsCategory.build();
            }).get())
            .build().generateScreen(parent);
    }
}
