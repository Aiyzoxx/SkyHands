package net.skyhands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class SkyHandsPresets {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File PRESETS_DIR = new File(FabricLoader.getInstance().getConfigDir().toFile(), "skyhands_presets");

    public static class PresetData {
        public float scale = 1.0f;
        public int xOffset = 0;
        public int yOffset = 0;
        public int zOffset = 0;
        public float rotX = 0f;
        public float rotY = 0f;
        public float rotZ = 0f;

        public float swingSpeed = 1.0f;
        public boolean constantSwingSpeed = false;
        public boolean blockHitting = false;
        public boolean suppressBobbing = false;
        public int swingDriftX = 0;
        public int swingDriftY = 0;
        public int swingDriftZ = 0;
        public int swingArcX = 0;
        public int swingArcY = 0;
        public int swingArcZ = 0;

        public float offScale = 0.6f;
        public int offX = 0;
        public int offY = 0;
        public int offZ = 0;
        public boolean swingCounterRotation = false;
        public boolean cancelReEquip = false;
        public boolean affectItem = true;
        public boolean affectArm = true;
        public boolean rotationlessDrink = false;
    }

    public static void init() {
        if (!PRESETS_DIR.exists()) {
            PRESETS_DIR.mkdirs();
        }
    }

    public static List<String> getPresetNames() {
        init();
        List<String> names = new ArrayList<>();
        File[] files = PRESETS_DIR.listFiles((dir, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                names.add(file.getName().replace(".json", ""));
            }
        }
        return names;
    }

    public static void savePreset(String name) {
        init();
        try {
            PresetData data = new PresetData();
            data.scale = SkyHandsConfig.Position.scale;
            data.xOffset = SkyHandsConfig.Position.xOffset;
            data.yOffset = SkyHandsConfig.Position.yOffset;
            data.zOffset = SkyHandsConfig.Position.zOffset;
            data.rotX = SkyHandsConfig.Position.rotX;
            data.rotY = SkyHandsConfig.Position.rotY;
            data.rotZ = SkyHandsConfig.Position.rotZ;

            data.swingSpeed = SkyHandsConfig.Animations.swingSpeed;
            data.constantSwingSpeed = SkyHandsConfig.Animations.constantSwingSpeed;
            data.blockHitting = SkyHandsConfig.Animations.blockHitting;
            data.suppressBobbing = SkyHandsConfig.Animations.suppressBobbing;
            data.swingDriftX = SkyHandsConfig.Animations.swingDriftX;
            data.swingDriftY = SkyHandsConfig.Animations.swingDriftY;
            data.swingDriftZ = SkyHandsConfig.Animations.swingDriftZ;
            data.swingArcX = SkyHandsConfig.Animations.swingArcX;
            data.swingArcY = SkyHandsConfig.Animations.swingArcY;
            data.swingArcZ = SkyHandsConfig.Animations.swingArcZ;

            data.offScale = SkyHandsConfig.Offhand.offScale;
            data.offX = SkyHandsConfig.Offhand.offX;
            data.offY = SkyHandsConfig.Offhand.offY;
            data.offZ = SkyHandsConfig.Offhand.offZ;
            data.swingCounterRotation = SkyHandsConfig.Offhand.swingCounterRotation;
            data.cancelReEquip = SkyHandsConfig.Offhand.cancelReEquip;
            data.affectItem = SkyHandsConfig.Offhand.affectItem;
            data.affectArm = SkyHandsConfig.Offhand.affectArm;
            data.rotationlessDrink = SkyHandsConfig.Offhand.rotationlessDrink;

            File file = new File(PRESETS_DIR, name + ".json");
            try (FileWriter writer = new FileWriter(file)) {
                GSON.toJson(data, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadPreset(String name) {
        File file = new File(PRESETS_DIR, name + ".json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                PresetData data = GSON.fromJson(reader, PresetData.class);
                if (data != null) {
                    SkyHandsConfig.Position.scale = data.scale;
                    SkyHandsConfig.Position.xOffset = data.xOffset;
                    SkyHandsConfig.Position.yOffset = data.yOffset;
                    SkyHandsConfig.Position.zOffset = data.zOffset;
                    SkyHandsConfig.Position.rotX = data.rotX;
                    SkyHandsConfig.Position.rotY = data.rotY;
                    SkyHandsConfig.Position.rotZ = data.rotZ;

                    SkyHandsConfig.Animations.swingSpeed = data.swingSpeed;
                    SkyHandsConfig.Animations.constantSwingSpeed = data.constantSwingSpeed;
                    SkyHandsConfig.Animations.blockHitting = data.blockHitting;
                    SkyHandsConfig.Animations.suppressBobbing = data.suppressBobbing;
                    SkyHandsConfig.Animations.swingDriftX = data.swingDriftX;
                    SkyHandsConfig.Animations.swingDriftY = data.swingDriftY;
                    SkyHandsConfig.Animations.swingDriftZ = data.swingDriftZ;
                    SkyHandsConfig.Animations.swingArcX = data.swingArcX;
                    SkyHandsConfig.Animations.swingArcY = data.swingArcY;
                    SkyHandsConfig.Animations.swingArcZ = data.swingArcZ;

                    SkyHandsConfig.Offhand.offScale = data.offScale;
                    SkyHandsConfig.Offhand.offX = data.offX;
                    SkyHandsConfig.Offhand.offY = data.offY;
                    SkyHandsConfig.Offhand.offZ = data.offZ;
                    SkyHandsConfig.Offhand.swingCounterRotation = data.swingCounterRotation;
                    SkyHandsConfig.Offhand.cancelReEquip = data.cancelReEquip;
                    SkyHandsConfig.Offhand.affectItem = data.affectItem;
                    SkyHandsConfig.Offhand.affectArm = data.affectArm;
                    SkyHandsConfig.Offhand.rotationlessDrink = data.rotationlessDrink;

                    SkyHandsClient.CONFIGURATOR.saveConfig(SkyHandsConfig.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deletePreset(String name) {
        File file = new File(PRESETS_DIR, name + ".json");
        if (file.exists()) {
            file.delete();
        }
    }
}
