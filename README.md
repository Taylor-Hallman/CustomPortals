# Custom Portals (NeoForge)
Custom Portals is a mod that allows you to construct inter- and intradimensional portals out of any block.

This repository is a native **NeoForge** port of [Palyon's Custom Portals](https://github.com/Palyon-dev/CustomPortals) for Minecraft 26.1. The original mod targets Fabric; download it from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/custom-portals) or [Modrinth](https://modrinth.com/mod/custom-portals).

## Building
Requires Java 25. Run `./gradlew build`; the jar is written to `build/libs/`.

## Configuration
`config/customportals-common.toml`, also editable from the Mods screen (Custom Portals > Config).

## Differences from the Fabric version
- No dependencies (no Fabric API, YACL, Mod Menu, or Cardinal Components).
- Portal data is stored in the world's `data/` folder; worlds from the Fabric version keep their blocks but portals must be rebuilt.
