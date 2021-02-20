## Ivan Carpet Addition

[![License](https://img.shields.io/github/license/Ivan-YFw/Ivan-Carpet-Addition.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/Ivan-YFw/Ivan-Carpet-Addition.svg)](https://github.com/Ivan-YFw/Ivan-Carpet-Addition/issues)

**English** / [中文](https://github.com/Ivan-YFw/Ivan-Carpet-Addition/blob/fabric-1.15.2/README_CN.md)

A [Carpet mod](https://github.com/gnembon/fabric-carpet) (fabric-carpet) extension, a collection of carpet mod style useful tools and interesting features.

Use with carpet mod in the same Minecraft version. Use newer carpet mod versions whenever possible

## Index

### [Rules](#rule-list)

 - [customVersion](#customVersion)
 - [icaSyncProtocol](#icaSyncProtocol)
 - [cobwebSlowDownSpeed](#cobwebSlowDownSpeed)
 - [fakePlayerPreset](#fakePlayerPreset)
 - [playerCommandNoControlSelf](#playerCommandNoControlSelf)
 - [flippinCactusSound](#flippinCactusSound)
 - [editableSign](#editableSign)
 - [bannedMobs](#bannedMobs)
 - [creeperDropCompletely](#creeperDropCompletely)
 - [creeperIgnitedByFire](#creeperIgnitedByFire)
 - [pistonBedrockBreakingFix](#pistonBedrockBreakingFix)
 - [blockEventChunkLoading](#blockEventChunkLoading)
 - [blockEventChunkLoadingTicks](#blockEventChunkLoadingTicks)
 - [villageChunkLoading](#villageChunkLoading)
 
## Rule List

### customVersion

Set a different version on client trying to connect to the server

Use '_' to disable

![screenshot](https://raw.githubusercontent.com/Ivan-YFw/Ivan-Carpet-Addition/fabric-1.15.2/screenshots/customVersion.png)

- Type: `String`  
- Default value: `_`  
- Suggested options: `1.19.6`
- Categories: `ICA`, `EXPERIMENTAL` 

### icaSyncProtocol

A protocol to sync Entities between the client and the server

- Type: `boolean`  
- Default value: `false`  
- Suggested options: `true`, `false`
- Categories: `ICA`, `PROTOCOL` 

### cobwebSlowDownSpeed

Overwrite the speed the cobweb slow down entities

- Type: `double`  
- Default value: `0.05000000074505806`  
- Suggested options: `0.05000000074505806`, `0.1`, `0.5`
- Categories: `ICA`, `CREATIVE` 

### fakePlayerPreset

Override the player list when using `/player` command

Use `,` to split each name

- Type: `String`
- Default value: `Steve,Alex`  
- Suggested options: `Steve,Alex`, `Steve,Alex,bot_loader`, `bot_loader`
- Categories: `ICA`, `CREATIVE`, `SURVIVAL` 

![screenshot](https://raw.githubusercontent.com/Ivan-YFw/Ivan-Carpet-Addition/fabric-1.15.2/screenshots/fakePlayerPreset.png)

### playerCommandNoControlSelf

Players can't control themselves using `/player` command

- Type: `boolean`
- Default value: `false`  
- Suggested options: `true`, `false`
- Categories: `ICA`, `SURVIVAL` 

### flippinCactusSound

Play `BLOCK_DISPENSER_LAUNCH` sound when using cactus to flip block

- Type: `boolean`
- Default value: `false`  
- Suggested options: `true`, `false`
- Categories: `ICA`, `CREATIVE`, `SURVIVAL` 

### editableSign

Right click a sign block with an empty hand when you are sneaking to reopen the sign editor

- Type: `boolean`
- Default value: `false`  
- Suggested options: `true`, `false`
- Categories: `ICA`, `EXPERIMENTAL` 

### bannedMobs

Banned some mobs from the server

Use ',' to split each mob

Use '_' to disable

**Warning**: This will also remove the **existing** mobs

- Type: `String`
- Default value: `_`  
- Suggested options: `zombie`, `skeleton`, `zombie,skeleton`
- Categories: `ICA`, `CREATIVE` 

### creeperDropCompletely

Creeper explosion 100% drop

- Type: `boolean`
- Default value: `false`  
- Suggested options: `true`, `false`
- Categories: `ICA`, `FEATURE` 

### creeperIgnitedByFire

Creepers can be ignited when they are on fire

- Type: `boolean`
- Default value: `false`  
- Suggested options: `true`, `false`
- Categories: `ICA`, `FEATURE` 

### pistonBedrockBreakingFix

Fix bedrock breaking with head-less piston

- Type: `boolean`
- Default value: `false`  
- Suggested options: `true`, `false`
- Categories: `ICA`, `BUGFIX` 

### blockEventChunkLoading

Block event can load chunks

- Type: `boolean`
- Default value: `false`  
- Suggested options: `true`, `false`
- Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### blockEventChunkLoadingTicks

The load duration of block event

- Type: `Integer`
- Default value: `4`  
- Suggested options: `4`, `8`, `16`
- Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### villageChunkLoading

A villager with a bed can load 3*3 chunks

- Type: `boolean`
- Default value: `false`  
- Suggested options: `true`, `false`
- Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`