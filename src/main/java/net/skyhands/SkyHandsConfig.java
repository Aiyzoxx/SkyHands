package net.skyhands;

import com.teamresourceful.resourcefulconfig.api.annotations.Config;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigOption;
import com.teamresourceful.resourcefulconfig.api.annotations.Category;

@Config(value = "skyhands", categories = {SkyHandsConfig.Position.class, SkyHandsConfig.Animations.class, SkyHandsConfig.Offhand.class})
public class SkyHandsConfig {
    
    @Category("Position")
    public static final class Position {
        @ConfigEntry(id = "scale", translation = "Scale")
        @ConfigOption.Slider
        @ConfigOption.Range(min = 0.05, max = 1.5)
        public static float scale = 1.0f;

        @ConfigEntry(id = "xOffset", translation = "X Offset")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -150.0, max = 150.0)
        public static int xOffset = 0;

        @ConfigEntry(id = "yOffset", translation = "Y Offset")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -150.0, max = 150.0)
        public static int yOffset = 0;

        @ConfigEntry(id = "zOffset", translation = "Z Offset")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -150.0, max = 150.0)
        public static int zOffset = 0;

        @ConfigEntry(id = "rotX", translation = "Rot X")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -180.0, max = 180.0)
        public static float rotX = 0f;

        @ConfigEntry(id = "rotY", translation = "Rot Y")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -180.0, max = 180.0)
        public static float rotY = 0f;

        @ConfigEntry(id = "rotZ", translation = "Rot Z")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -180.0, max = 180.0)
        public static float rotZ = 0f;
    }

    @Category("Animations")
    public static final class Animations {
        @ConfigEntry(id = "swingSpeed", translation = "Swing Speed")
        @ConfigOption.Slider
        @ConfigOption.Range(min = 0.1, max = 3.0)
        public static float swingSpeed = 1.0f;

        @ConfigEntry(id = "constantSwingSpeed", translation = "Constant Speed")
        public static boolean constantSwingSpeed = false;

        @ConfigEntry(id = "blockHitting", translation = "Block Hitting (1.8)")
        public static boolean blockHitting = false;

        @ConfigEntry(id = "suppressBobbing", translation = "Suppress Bobbing")
        public static boolean suppressBobbing = false;

        @ConfigEntry(id = "swingDriftX", translation = "Drift X")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -150.0, max = 150.0)
        public static int swingDriftX = 0;

        @ConfigEntry(id = "swingDriftY", translation = "Drift Y")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -150.0, max = 150.0)
        public static int swingDriftY = 0;

        @ConfigEntry(id = "swingDriftZ", translation = "Drift Z")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -150.0, max = 150.0)
        public static int swingDriftZ = 0;

        @ConfigEntry(id = "swingArcX", translation = "Arc X")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -150.0, max = 150.0)
        public static int swingArcX = 0;

        @ConfigEntry(id = "swingArcY", translation = "Arc Y")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -150.0, max = 150.0)
        public static int swingArcY = 0;

        @ConfigEntry(id = "swingArcZ", translation = "Arc Z")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -150.0, max = 150.0)
        public static int swingArcZ = 0;
    }

    @Category("Offhand")
    public static final class Offhand {
        @ConfigEntry(id = "offScale", translation = "Offhand Scale")
        @ConfigOption.Slider
        @ConfigOption.Range(min = 0.05, max = 1.5)
        public static float offScale = 0.6f;

        @ConfigEntry(id = "offX", translation = "Off X")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -150.0, max = 150.0)
        public static int offX = 0;

        @ConfigEntry(id = "offY", translation = "Off Y")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -150.0, max = 150.0)
        public static int offY = 0;

        @ConfigEntry(id = "offZ", translation = "Off Z")
        @ConfigOption.Slider
        @ConfigOption.Range(min = -150.0, max = 150.0)
        public static int offZ = 0;

        @ConfigEntry(id = "swingCounterRotation", translation = "Counter Rot")
        public static boolean swingCounterRotation = false;

        @ConfigEntry(id = "cancelReEquip", translation = "Cancel ReEquip")
        public static boolean cancelReEquip = false;

        @ConfigEntry(id = "affectItem", translation = "Affect Item")
        public static boolean affectItem = true;

        @ConfigEntry(id = "affectArm", translation = "Affect Arm")
        public static boolean affectArm = true;

        @ConfigEntry(id = "rotationlessDrink", translation = "Rotless Drink")
        public static boolean rotationlessDrink = false;
    }
}
