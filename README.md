## Ivan Carpet Addition

[![License](https://img.shields.io/github/license/Ivan-1F/Ivan-Carpet-Addition.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/Ivan-1F/Ivan-Carpet-Addition.svg)](https://github.com/Ivan-1F/Ivan-Carpet-Addition/issues)
[![MC Versions](http://cf.way2muchnoise.eu/versions/For%20MC_ivan-carpet-addition_all.svg)](https://www.curseforge.com/minecraft/mc-mods/ivan-carpet-addition)
[![CurseForge](http://cf.way2muchnoise.eu/full_ivan-carpet-addition_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/ivan-carpet-addition)

**English** / [中文](https://github.com/Ivan-1F/Ivan-Carpet-Addition/blob/fabric-1.15.2/README_CN.md)

A [Carpet mod](https://github.com/gnembon/fabric-carpet) (fabric-carpet) extension, a collection of carpet mod style useful tools and interesting features.

Use with carpet mod in the same Minecraft version. Use newer carpet mod versions whenever possible

## Index

### [Rules](#rule-list)

 - [customVersion](#customVersion)
 - [cobwebSlowDownSpeed](#cobwebSlowDownSpeed)
 - [fakePlayerPreset](#fakePlayerPreset)
 - [playerCommandNoControlSelf](#playerCommandNoControlSelf)
 - [flippinCactusSound](#flippinCactusSound)
 - [editableSign](#editableSign)
 - [mobBlackList](#mobBlackList)
 - [mobWhiteList](#mobWhiteList)
 - [mobSpawningRestrictionMode](#mobSpawningRestrictionMode)
 - [creeperDropCompletely](#creeperDropCompletely)
 - [creeperIgnitedByFire](#creeperIgnitedByFire)
 - [pistonBedrockBreakingFix](#pistonBedrockBreakingFix)
 - [blockEventChunkLoading](#blockEventChunkLoading)
 - [blockEventChunkLoadingTicks](#blockEventChunkLoadingTicks)
 - [villageChunkLoading](#villageChunkLoading)
 - [mendableIronGolem](#mendableIronGolem)
 - [mendableSnowGolem](#mendableSnowGolem)
 - [spongeDryInNether](#spongeDryInNether)
 - [magmaBlockDamageItem](#magmaBlockDamageItem)
 - [functionalSpongeItem](#functionalSpongeItem)
 - [fakePlayerPrefixCheck](#fakePlayerPrefixCheck)
 - [fakePlayerSuffixCheck](#fakePlayerSuffixCheck)
 - [infiniteWaterDisabled](#infiniteWaterDisabled)
 - [renewableSoulSand](#renewableSoulSand)
 - [containerDropInventoryDisabled](#containerDropInventoryDisabled)
 - [endLightningRod](#endLightningRod)
 - [undeadImmuneToSunlight](#undeadImmuneToSunlight)
 - [unbreakableHelmetInSunlight](#unbreakableHelmetInSunlight)
 - [mobAlwaysPickUpLoot](#mobAlwaysPickUpLoot)
 - [dispensersNotAffectPlayers](#dispensersNotAffectPlayers)
 
### Others

 - [development](#Development)
 
## Rule List

### customVersion

Set a custom version on client trying to connect to the server

Use `_` to disable

 - Type: `String`
 - Default value: `_`
 - Suggested options: `_`
 - Categories: `ICA`, `EXPERIMENTAL`

![screenshot](https://raw.githubusercontent.com/Ivan-YFw/Ivan-Carpet-Addition/fabric-1.15.2/screenshots/customVersion.png)

### cobwebSlowDownSpeed

Override the slow down speed of cobwebs

 - Type: `double`
 - Default value: `0.05000000074505806D`
 - Suggested options: `0.05000000074505806`
 - Categories: `ICA`, `CREATIVE`

### fakePlayerPreset

Override the player list when using /player command

Use `,` to split each name

 - Type: `String`
 - Default value: `Steve,Alex`
 - Suggested options: `Steve,Alex`, `Steve,Alex,bot_loader`, `bot_loader`
 - Categories: `ICA`, `CREATIVE`, `SURVIVAL`

![screenshot](https://raw.githubusercontent.com/Ivan-YFw/Ivan-Carpet-Addition/fabric-1.15.2/screenshots/fakePlayerPreset.png)

### playerCommandNoControlSelf

Players can't control themselves using /player command

 - Type: `boolean`
 - Default value: `false`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `SURVIVAL`

### flippinCactusSound

Play 'BLOCK_DISPENSER_LAUNCH' sound when using cactus to flip block

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

### mobBlackList

Avoid some mobs from spawning

Use `,` to split each mob

Set rule `mobSpawningRestrictionMode` to `blacklist` to enable

Set rule `mobSpawningRestrictionMode` to `none` to disable

 - Type: `String`
 - Default value: `_`
 - Suggested options: `_`, `zombie`, `skeleton`, `zombie,skeleton`
 - Categories: `ICA`, `CREATIVE`

### mobWhiteList

Only allow some mobs to spawn

Use `,` to split each mob

Set rule `mobSpawningRestrictionMode` to `whitelist` to enable

Set rule `mobSpawningRestrictionMode` to `none` to disable

 - Type: `String`
 - Default value: `_`
 - Suggested options: `_`, `zombie`, `skeleton`, `zombie,skeleton`
 - Categories: `ICA`, `CREATIVE`

### mobSpawningRestrictionMode

Modify the way to restrict mob spawning (black list or white list)

Set the list with rule `mobBlackList` and `mobWhiteList`

 - Type: `String`
 - Default value: `none`
 - Suggested options: `none`, `whitelist`, `blacklist`
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

 - Type: `int`
 - Default value: `4`
 - Suggested options: `4`, `8`, `16`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### villageChunkLoading

A villager with a bed can load 3*3 chunks

 - Type: `boolean`
 - Default value: `false`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### mendableIronGolem

Right click a iron golem with the iron ingot to mend it (+25 Health)

 - Type: `boolean`
 - Default value: 
    - 1.14: `false`
    - 1.15+: `true`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `FEATURE`

### mendableSnowGolem

Right click a snow golem with the snowball or hit it with the snowball to mend it (+1 Health)

 - Type: `boolean`
 - Default value: `false`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### spongeDryInNether

Wet sponge will dry in nether

 - Type: `boolean`
 - Default value: 
    - 1.14: `false`
    - 1.15+: `true`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `FEATURE`

### magmaBlockDamageItem

Items on magma block get damage

 - Type: `boolean`
 - Default value: `true`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### functionalSpongeItem

Sponge item do water clearance and dry in the nether

 - Type: `boolean`
 - Default value: `false`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### fakePlayerPrefixCheck

Check the prefix of fake players when using player command

Set to #none to disable

 - Type: `String`
 - Default value: `#none`
 - Suggested options: `#none`, `bot_`
 - Categories: `ICA`, `SURVIVAL`, `CREATIVE`

### fakePlayerSuffixCheck

Check the suffix of fake players when using player command

Set to #none to disable

 - Type: `String`
 - Default value: `#none`
 - Suggested options: `#none`, `_fake`
 - Categories: `ICA`, `SURVIVAL`, `CREATIVE`

### infiniteWaterDisabled

Water will act like lava

 - Type: `boolean`
 - Default value: `false`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### renewableSoulSand

A sand turn into a soul sand when a mob suffered in it

 - Type: `boolean`
 - Default value: `false`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `FEATURE`, `SURVIVAL`

### containerDropInventoryDisabled

Containers such as chests and barrels won't drop their inventories when removal

 - Type: `boolean`
 - Default value: `false`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `CREATIVE`

### endLightningRod

End rods will act like lightning rod in 1.17

Lightning will NOT naturally spawn on end rods, but it will when end rods are hit by a trident with the Channeling enchantment

 - Type: `boolean`
 - Default value: `false`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `FEATURE`

### undeadImmuneToSunlight

Undead will not burn in sunlight

If a undead is equipped with a helmet, the helmet will not be damaged

 - Type: `boolean`
 - Default value: `false`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### unbreakableHelmetInSunlight

Helmet equipped by undead will not be damaged in sunlight

 - Type: `boolean`
 - Default value: `false`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### mobAlwaysPickUpLoot

Zombies and skeletons and their variants will always be able to pick up loot like other mobs

 - Type: `boolean`
 - Default value: `false`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `CREATIVE`

### dispensersNotAffectPlayers

Dispensers will not dispense armor to players

 - Type: `boolean`
 - Default value: `false`
 - Suggested options: `true`, `false`
 - Categories: `ICA`, `CREATIVE`

## Development

Current main development branch: **1.15.2**

Current maintaining branches:
 - fabric-1.14.4, for Minecraft 1.14.4
 - fabric-1.15.2, for Minecraft 1.15.2
 - fabric-1.16.4, for Minecraft 1.16.4 to 1.16.5
 - fabric-1.17, for Minecraft 1.17 snapshots

For general new features, implement them in 1.15.2 branch first then merge it into other branches

Branches merge order:
 - 1.15.2 -> 1.14.4
 - 1.15.2 -> 1.16.4 -> 1.17
 
For version-specific fixes / patches, implement them in relevant branches

The English doc and the Chinese doc are aligned line by line btw