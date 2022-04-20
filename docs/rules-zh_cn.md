# 规则

[English](rules.md) | **中文**

### 方块事件加载区块 (blockEventChunkLoading)

方块事件发生时会加载 3x3 的区块 8gt

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `FEATURE`, `EXPERIMENTAL`

### 蜘蛛网减缓实体的速度 (cobwebSlowdownSpeed)

覆写蜘蛛网减缓实体的速度

 - 类型: `double`
 - 默认值: `0.05000000074505806`
 - 参考选项: `0.05000000074505806`
 - 分类: `ICA`, `CREATIVE`

### 容器不掉落物品栏 (containerDropInventoryDisabled)

像箱子和木桶一样的容器在被破坏时不会掉落它们的物品栏

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `CREATIVE`

### 苦力怕完全掉落 (creeperDropCompletely)

苦力怕爆炸破坏的方块将有 100% 的掉落率

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `FEATURE`

### 苦力怕被火点燃 (creeperIgnitedByFire)

苦力怕在燃烧状态时会被点燃

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `FEATURE`

### 自定义版本 (customVersion)

自定义客户端服务器列表中显示的版本信息

设置为 '_' 以禁用

 - 类型: `String`
 - 默认值: `_`
 - 参考选项: `_`
 - 分类: `ICA`, `EXPERIMENTAL`

### 发射器不会影响玩家 (dispensersNotAffectPlayers)

发射器将不会向玩家发射装备

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `CREATIVE`

### 可编辑告示牌 (editableSign)

当你在潜行状态下用空手使用一个告示牌时重新打开告示牌编辑界面

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `EXPERIMENTAL`, `SURVIVAL`

### 末地烛避雷针 (endLightningRod)

末地烛将会像 1.17 中的避雷针一样

末地烛不会自然引雷，但会在被附有引雷附魔的三叉戟击中时在上方生成闪电束（与避雷针表现一致）

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `FEATURE`

### 假人名称补全预设 (fakePlayerNameSuggestions)

覆写使用 '/player' 指令时建议的假人名称列表

使用 ',' 分割每一个名字

 - 类型: `String`
 - 默认值: `Steve,Alex`
 - 参考选项: `Steve,Alex`, `Steve,Alex,bot_loader`, `bot_loader`
 - 分类: `ICA`, `CREATIVE`, `SURVIVAL`

### 假人名称前缀限制 (fakePlayerPrefixRestriction)

/player 指令将只能生成以给定前缀为名的假人

设置为 '#none' 以禁用

 - 类型: `String`
 - 默认值: `#none`
 - 参考选项: `#none`, `bot_`
 - 分类: `ICA`, `SURVIVAL`, `CREATIVE`

### 假人名称后缀限制 (fakePlayerSuffixRestriction)

/player 指令将只能生成以给定后缀为名的假人

设置为 '#none' 以禁用

 - 类型: `String`
 - 默认值: `#none`
 - 参考选项: `#none`, `_fake`
 - 分类: `ICA`, `SURVIVAL`, `CREATIVE`

### 仙人掌扳手音效 (flippinCactusSound)

使用仙人掌扳手时播放 'BLOCK_DISPENSER_LAUNCH' 音效

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `CREATIVE`, `SURVIVAL`

### 功能性海绵物品 (functionalSpongeItem)

海绵的掉落物实体形态可以吸水，在地狱的湿海绵掉落物会在 60gt 后转换为海绵

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `FEATURE`, `EXPERIMENTAL`

### ICA同步协议 (icaSyncProtocol)

一个用于将服务器数据同步至客户端模组的同步协议

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `PROTOCOL`

### 禁用无限水 (infiniteWaterDisabled)

无限水将不会形成

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `FEATURE`, `EXPERIMENTAL`

### 岩浆块伤害物品 (magmaBlockDamageItem)

岩浆块会对物品实体造成伤害

 - 类型: `boolean`
 - 默认值: `true`
 - 参考选项: `true`
 - 分类: `ICA`, `FEATURE`

### 可修复铁傀儡 (mendableIronGolem)

对铁傀儡使用铁锭使它恢复 25 点血量

仅在 1.14 有效

 - 类型: `boolean`
 - 默认值: `true`
 - 参考选项: `true`
 - 分类: `ICA`, `FEATURE`, `PORTING`

### 可修复雪傀儡 (mendableSnowGolem)

对雪傀儡使用雪球或使用雪球击中雪傀儡使它恢复 1 点血量

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `FEATURE`, `EXPERIMENTAL`

### 生物总是可以捡起物品 (mobAlwaysPickUpLoot)

僵尸和骷髅及其变种将总是可以像其他生物一样捡起物品

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `CREATIVE`

### 生物黑名单 (mobBlackList)

阻止一些特定的生物生成

使用 ',' 分割每个生物

将规则 生物生成限制模式(mobSpawningRestrictionMode) 设定为 'blacklist' 来启用

将规则 生物生成限制模式(mobSpawningRestrictionMode) 设定为 'none' 来禁用

 - 类型: `String`
 - 默认值: `_`
 - 参考选项: `_`, `zombie`, `skeleton`, `zombie,skeleton`
 - 分类: `ICA`, `CREATIVE`

### 生物生成限制模式 (mobSpawningRestrictionMode)

修改生物生成的限制模式

whitelist: 只有在规则 生物白名单(mobWhiteList) 中声明的生物可以在世界中生成。规则 生物黑名单(mobBlackList) 将被忽略

blacklist: 规则 生物黑名单(mobBlackList) 中声明的生物无法在世界中生成。规则 生物白名单(mobWhiteList) 将被忽略

 - 类型: `String`
 - 默认值: `none`
 - 参考选项: `none`, `whitelist`, `blacklist`
 - 分类: `ICA`, `CREATIVE`

### 生物白名单 (mobWhiteList)

仅允许一些特定的生物生成

使用 ',' 分割每个生物

将规则 生物生成限制模式(mobSpawningRestrictionMode) 设定为 'whitelist' 来启用

将规则 生物生成限制模式(mobSpawningRestrictionMode) 设定为 'none' 来禁用

 - 类型: `String`
 - 默认值: `_`
 - 参考选项: `_`, `zombie`, `skeleton`, `zombie,skeleton`
 - 分类: `ICA`, `CREATIVE`

### 无头活塞破基岩修复 (pistonBedrockBreakingFix)

修复使用无头活塞的基岩破除

技术上活塞头将不会移除除自身以外的任何其他方块

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `BUGFIX`

### player指令不准控制自己 (playerCommandNoControlSelf)

玩家无法使用 '/player' 指令控制自己

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `SURVIVAL`

### 可再生灵魂沙 (renewableSoulSand)

生物在沙子中窒息时沙子转换为灵魂沙

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `FEATURE`, `SURVIVAL`

### 湿海绵在地狱蒸发 (spongeDryInNether)

湿海绵放置在地狱中时会立即转变为海绵

仅在 1.14 中有效

 - 类型: `boolean`
 - 默认值: `true`
 - 参考选项: `true`
 - 分类: `ICA`, `FEATURE`, `PORTING`

### 禁止结冰 (stopFreezing)

在任何群系禁止冰的生成

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `FEATURE`

### 严格方块防止 (strictBlockPlacement)

玩家无法在空气上防止方块（阻止 litematica 轻松放置）

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `SURVIVAL`

### 亡灵生物头盔不可被阳光破坏 (unbreakableHelmetInSunlight)

亡灵生物装备的头盔在阳光下不会消耗耐久

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `FEATURE`, `EXPERIMENTAL`

### 亡灵生物免疫阳光 (undeadImmuneToSunlight)

亡灵生物将不在白天燃烧

若一只亡灵生物装备有头盔，头盔也不会消耗耐久

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `FEATURE`, `EXPERIMENTAL`

### 经验记数器 (xpCounter)

使用玩家作为经验计数器

启用 '/xpcounter' 指令

使用 '/xpcounter <player> reset' 重置计数器

使用 '/xpcounter <player>' 查询计数器

使用 '/log xpcounter <players>' 订阅计数器

 - 类型: `boolean`
 - 默认值: `false`
 - 参考选项: `false`
 - 分类: `ICA`, `CREATIVE`, `FEATURE`

