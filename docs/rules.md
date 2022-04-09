**English** | [中文](rules-zh_cn.md)

### customVersion

Set a custom version on client trying to connect to the server

Use '_' to disable

 - Type: String
 - Default value: `_`
 - Suggested options: `_`
 - Categories: `ICA`, `EXPERIMENTAL`

### cobwebSlowdownSpeed

Overwrite the slowdown speed of cobwebs

 - Type: double
 - Default value: `0.05000000074505806`
 - Suggested options: `0.05000000074505806`
 - Categories: `ICA`, `CREATIVE`

### fakePlayerNameSuggections

Overwrite the player list suggested when using /player command

Use ',' to split each name

 - Type: String
 - Default value: `Steve,Alex`
 - Suggested options: `Steve,Alex`, `Steve,Alex,bot_loader`, `bot_loader`
 - Categories: `ICA`, `CREATIVE`, `SURVIVAL`

### playerCommandNoControlSelf

Players can't control themselves using /player command

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `SURVIVAL`

### flippinCactusSound

Play 'BLOCK_DISPENSER_LAUNCH' sound when using cactus to flip block

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `CREATIVE`, `SURVIVAL`

### editableSign

Use a sign block with main hand empty when you are sneaking to reopen the sign editor

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `EXPERIMENTAL`, `SURVIVAL`

### mobBlackList

Stop some mobs from spawning

Use ',' to split each mob

Set rule 'mobSpawningRestrictionMode' to 'blacklist' to enable

Set rule 'mobSpawningRestrictionMode' to 'none' to disable

 - Type: String
 - Default value: `_`
 - Suggested options: `_`, `zombie`, `skeleton`, `zombie,skeleton`
 - Categories: `ICA`, `CREATIVE`

### mobWhiteList

Only allow some mobs to spawn

Use ',' to split each mob

Set rule 'mobSpawningRestrictionMode' to 'whitelist' to enable

Set rule 'mobSpawningRestrictionMode' to 'none' to disable

 - Type: String
 - Default value: `_`
 - Suggested options: `_`, `zombie`, `skeleton`, `zombie,skeleton`
 - Categories: `ICA`, `CREATIVE`

### mobSpawningRestrictionMode

Modify the way to restrict mob spawning

whitelist: Only mobs defined in rule 'mobWhiteList' can spawn in the world. Rule 'mobBlackList' will be ignored

blacklist: Mobs defined in rule 'mobBlackList' cannot spawn in the world. Rule 'mobWhiteList' will be ignored

 - Type: String
 - Default value: `none`
 - Suggested options: `none`, `whitelist`, `blacklist`
 - Categories: `ICA`, `CREATIVE`

### creeperDropCompletely

Creeper explosions will have a 100% drop rate

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `FEATURE`

### creeperIgnitedByFire

Creepers can be ignited when they are on fire

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `FEATURE`

### pistonBedrockBreakingFix

Fix bedrock breaking with head-less piston

Technically piston heads will not remove any block other than itself

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `BUGFIX`

### blockEventChunkLoading

Block event can load 3x3 chunks for 8gt

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### mendableIronGolem

Use an iron ingot at an iron golem to mend it (+25 Health)

Only works in 1.14

 - Type: boolean
 - Default value: `true`
 - Suggested options: `true`
 - Categories: `ICA`, `FEATURE`, `BACKPORT`

### mendableSnowGolem

Use a snowball at a snow golem or hit a snow golem with a snowball to mend it (+1 Health)

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### spongeDryInNether

Wet sponge will transform to sponge immediately when placing in the nether

Only works in 1.14

 - Type: boolean
 - Default value: `true`
 - Suggested options: `true`
 - Categories: `ICA`, `FEATURE`, `BACKPORT`

### magmaBlockDamageItem

Items on magma block get damages

 - Type: boolean
 - Default value: `true`
 - Suggested options: `true`
 - Categories: `ICA`, `FEATURE`

### functionalSpongeItem

Sponge items do water clearance and dry in the nether after 60gt

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### fakePlayerPrefixRestriction

/player command will only be able to spawn fake players with the given prefix

Set to '#none' to disable

 - Type: String
 - Default value: `#none`
 - Suggested options: `#none`, `bot_`
 - Categories: `ICA`, `SURVIVAL`, `CREATIVE`

### fakePlayerSuffixRestriction

Player command will only be able to spawn fake players with the given suffix

Set to '#none' to disable

 - Type: String
 - Default value: `#none`
 - Suggested options: `#none`, `_fake`
 - Categories: `ICA`, `SURVIVAL`, `CREATIVE`

### infiniteWaterDisabled

Infinite water will not form

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### renewableSoulSand

A sand turn into a soul sand when a mob suffered in it

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `FEATURE`, `SURVIVAL`

### containerDropInventoryDisabled

Containers such as chests and barrels won't drop their inventories when being broke

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `CREATIVE`

### endLightningRod

End rods will act like lightning rod in 1.17

Lightning will NOT naturally spawn on end rods, but it will when end rods are hit by a trident with the Channeling enchantment

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `FEATURE`

### undeadImmuneToSunlight

Undead will not burn in sunlight

If a undead is equipped with a helmet, the helmet will not be damaged

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### unbreakableHelmetInSunlight

Helmet equipped by an undead will not be damaged by sunlight

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `FEATURE`, `EXPERIMENTAL`

### mobAlwaysPickUpLoot

Zombies and skeletons and their variants will always be able to pick up loot like other mobs

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `CREATIVE`

### dispensersNotAffectPlayers

Dispensers will not dispense armor to players

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `CREATIVE`

### icaSyncProtocol

A protocol to sync server data to client mods

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `PROTOCOL`

### stopFreezing

Stop generating ice at any biome

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `FEATURE`

### experienceCounter

Use players as experience counters

Enables /xpcounter command

Use /xpcounter <player> reset to reset the counter

Use /xpcounter <player> to query the counter

 - Type: boolean
 - Default value: `false`
 - Suggested options: `false`
 - Categories: `ICA`, `CREATIVE`, `FEATURE`
