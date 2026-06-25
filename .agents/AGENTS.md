# Antigravity Rules for SkyHands

## Compilation & Versioning (CRITICAL)

When working on this Minecraft Mod (SkyHands), you MUST adhere to the following compilation rules:

1. **DO NOT upgrade the Gradle project to Minecraft 26.x or newer.** 
   - Mojang did not release official mappings (`client_mappings`) for 26.1.2.
   - Fabric Yarn mappings also do not exist for 26.x.
   - Any attempt to use `loom.officialMojangMappings()` with Minecraft 26.1.2 will fail with `Failed to find official mojang mappings for 26.1.2`.
   - Compiling for 26.x also requires JDK 25, which complicates the environment.

2. **Always compile against Minecraft 1.20.4 and Java 21.**
   - The environment is correctly set up for 1.20.4 in `gradle.properties` and `build.gradle`.
   - `JAVA_HOME` should point to a JDK 21 installation (e.g. `C:\Users\Valentin\Music\smallhandedit\jdk\jdk-21`).

3. **How cross-version compatibility is achieved:**
   - The mod is intentionally compiled for 1.20.4.
   - In `src/main/resources/fabric.mod.json`, the `minecraft` dependency is set to `"*"` (wildcard).
   - Because the mod is mechanically simple, the 1.20.4 compiled `.jar` works flawlessly when loaded in Minecraft 26.1.2.
   - If you need to change an asset (like `icon.png`), compile in 1.20.4, OR simply inject the new asset directly into the previously compiled `.jar` using archive tools (bypassing Gradle entirely).
