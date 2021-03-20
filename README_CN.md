## Ivan Carpet Addition

[![License](https://img.shields.io/github/license/Ivan-YFw/Ivan-Carpet-Addition.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/Ivan-YFw/Ivan-Carpet-Addition.svg)](https://github.com/Ivan-YFw/Ivan-Carpet-Addition/issues)
[![MC Versions](http://cf.way2muchnoise.eu/versions/For%20MC_ivan-carpet-addition_all.svg)](https://www.curseforge.com/minecraft/mc-mods/ivan-carpet-addition)
[![CurseForge](http://cf.way2muchnoise.eu/full_ivan-carpet-addition_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/ivan-carpet-addition)

[English](https://github.com/Ivan-YFw/Ivan-Carpet-Addition/blob/fabric-1.15.2/README.md) / **中文**

这是一个 [Carpet mod](https://github.com/gnembon/fabric-carpet) (fabric-carpet) 的扩展 mod，包含了不少~~NotVanilla的~~有意思的功能以及特性

跟同 Minecraft 版本的 carpet mod 一起使用即可。尽可能地使用较新的 carpet mod

## 索引

### [规则](#规则列表)

 - [自定义版本](#自定义版本-customVersion)
 - [ICA同步协议](#ICA同步协议-icaSyncProtocol)
 - [蜘蛛网减缓实体的速度](#蜘蛛网减缓实体的速度-cobwebSlowDownSpeed)
 - [假人预设](#假人预设-fakePlayerPreset)
 - [player指令不准控制自己](#player指令不准控制自己-playerCommandNoControlSelf)
 - [仙人掌扳手音效](#仙人掌扳手音效-flippinCactusSound)
 - [可编辑告示牌](#可编辑告示牌-editableSign)
 - [生物生成限制模式](#生物生成限制模式-mobSpawningRestrictionMode)
 - [生物黑名单](#生物黑名单-mobBlackList)
 - [生物白名单](#生物白名单-mobWhiteList)
 - [苦力怕完全掉落](#苦力怕完全掉落-creeperDropCompletely)
 - [苦力怕被火点燃](#苦力怕被火点燃-creeperIgnitedByFire)
 - [无头活塞破基岩修复](#无头活塞破基岩修复-pistonBedrockBreakingFix)
 - [方块事件加载区块](#方块事件加载区块-blockEventChunkLoading)
 - [方块事件区块加载持续时间](#方块事件区块加载持续时间-blockEventChunkLoadingTicks)
 - [村庄加载区块](#村庄加载区块-villageChunkLoading)
 - [可修复铁傀儡](#可修复铁傀儡-mendableIronGolem)
 - [可修复雪傀儡](#可修复雪傀儡-mendableSnowGolem)
 - [湿海绵在地狱蒸发](#湿海绵在地狱蒸发-spongeDryInNether)
 - [岩浆块伤害物品](#岩浆块伤害物品-magmaBlockDamageItem)
 - [功能性海绵物品](#功能性海绵物品-functionalSpongeItem)
 - [假人名称前缀检查](#假人名称前缀检查-fakePlayerPrefixCheck)
 - [假人名称后缀检查](#假人名称后缀检查-fakePlayerSuffixCheck)

### 其他

 - [开发](#开发)

## 规则列表

### 自定义版本 (customVersion)

设置客户端服务器列表中显示的版本

设置为 '_' 来禁用

![screenshot](https://raw.githubusercontent.com/Ivan-YFw/Ivan-Carpet-Addition/fabric-1.15.2/screenshots/customVersion.png)

- 类型: `String`  
- 默认值: `_`  
- 参考选项: `1.19.6`
- 分类: `ICA`, `EXPERIMENTAL` 

### ICA同步协议 (icaSyncProtocol)

一个用于在服务端和客户端之间同步 Entity 的协议

- 类型: `boolean`  
- 默认值: `false`  
- 参考选项: `true`, `false`
- 分类: `ICA`, `PROTOCOL` 

### 蜘蛛网减缓实体的速度 (cobwebSlowDownSpeed)

覆写蜘蛛网减缓实体的速度

- 类型: `double`  
- 默认值: `0.05000000074505806`  
- 参考选项: `0.05000000074505806`, `0.1`, `0.5`
- 分类: `ICA`, `CREATIVE` 

### 假人预设 (fakePlayerPreset)

覆写使用 `/player` 指令时的假人列表

使用 `,` 分割每个名字

- 类型: `String`
- 默认值: `Steve,Alex`  
- 参考选项: `Steve,Alex`, `Steve,Alex,bot_loader`, `bot_loader`
- 分类: `ICA`, `CREATIVE`, `SURVIVAL` 

![screenshot](https://raw.githubusercontent.com/Ivan-YFw/Ivan-Carpet-Addition/fabric-1.15.2/screenshots/fakePlayerPreset.png)

### player指令不准控制自己 (playerCommandNoControlSelf)

玩家无法使用 `/player` 指令控制自己

- 类型: `boolean`
- 默认值: `false`  
- 参考选项: `true`, `false`
- 分类: `ICA`, `SURVIVAL` 

### 仙人掌扳手音效 (flippinCactusSound)

使用仙人掌扳手时播放 `BLOCK_DISPENSER_LAUNCH` 音效

- 类型: `boolean`
- 默认值: `false`  
- 参考选项: `true`, `false`
- 分类: `ICA`, `CREATIVE`, `SURVIVAL` 

### 可编辑告示牌 (editableSign)

当你在潜行状态下用空手右键一个告示牌时重新打开告示牌编辑界面

- 类型: `boolean`
- 默认值: `false`  
- 参考选项: `true`, `false`
- 分类: `ICA`, `EXPERIMENTAL` 

### 生物生成限制模式 (mobSpawningRestrictionMode)

修改生物生成的限制模式 (使用黑名单还是白名单)

使用规则 'mobBlackList' 和 'mobWhiteList' 来设定生物生成限制

- 类型: `String`
- 默认值: `_`  
- 参考选项: `none`, `whitelist`, `blacklist`
- 分类: `ICA`, `CREATIVE` 

### 生物黑名单 (mobBlackList)

阻止一些特定的生物生成

使用 `,` 分割每个生物

将规则 `mobSpawningRestrictionMode` 设定为 `blacklist` 来启用

将规则 `mobSpawningRestrictionMode` 设定为 `none` 来禁用

- 类型: `String`
- 默认值: `_`  
- 参考选项: `_`, `zombie`, `skeleton`, `zombie,skeleton`
- 分类: `ICA`, `CREATIVE` 

### 生物白名单 (mobWhiteList)

仅允许一些特定的生物生成

使用 `,` 分割每个生物

将规则 `mobSpawningRestrictionMode` 设定为 `whitelist` 来启用

将规则 `mobSpawningRestrictionMode` 设定为 `none` 来禁用

- 类型: `String`
- 默认值: `_`  
- 参考选项: `_`, `zombie`, `skeleton`, `zombie,skeleton`
- 分类: `ICA`, `CREATIVE` 

### 苦力怕完全掉落 (creeperDropCompletely)

苦力怕爆炸 100% 掉落

- 类型: `boolean`
- 默认值: `false`  
- 参考选项: `true`, `false`
- 分类: `ICA`, `FEATURE` 

### 苦力怕被火点燃 (creeperIgnitedByFire)

苦力怕在燃烧状态时会被点燃

- 类型: `boolean`
- 默认值: `false`  
- 参考选项: `true`, `false`
- 分类: `ICA`, `FEATURE` 

### 无头活塞破基岩修复 (pistonBedrockBreakingFix)

修复使用无头活塞的基岩破除

- 类型: `boolean`
- 默认值: `false`  
- 参考选项: `true`, `false`
- 分类: `ICA`, `BUGFIX` 

### 方块事件加载区块 (blockEventChunkLoading)

方块事件发生时会加载当前的区块

- 类型: `boolean`
- 默认值: `false`  
- 参考选项: `true`, `false`
- 分类: `ICA`, `FEATURE`, `EXPERIMENTAL`

### 方块事件区块加载持续时间 (blockEventChunkLoadingTicks)

方块事件发生时会加载当前的区块所持续的时间

- 类型: `Integer`
- 默认值: `4`  
- 参考选项: `4`, `8`, `16`
- 分类: `ICA`, `FEATURE`, `EXPERIMENTAL`

### 村庄加载区块 (villageChunkLoading)

一个绑定床的村民可加载 3*3 的区块

- 类型: `boolean`
- 默认值: `false`  
- 参考选项: `true`, `false`
- 分类: `ICA`, `FEATURE`, `EXPERIMENTAL`

### 可修复铁傀儡 (mendableIronGolem)

用铁锭右键铁傀儡使它恢复 25 点血量

- 类型: `boolean`
- 默认值:
    - 1.14: `false`
    - 1.15+: `true`
- 参考选项: `true`, `false`
- 分类: `ICA`, `FEATURE` 

### 可修复雪傀儡 (mendableSnowGolem)

用雪球右键或使用雪球击中雪傀儡使它恢复 1 点血量

- 类型: `boolean`
- 默认值: `false`  
- 参考选项: `true`, `false`
- 分类: `ICA`, `FEATURE` `EXPERIMENTAL`

### 湿海绵在地狱蒸发 (spongeDryInNether)

湿海绵放置在地狱中时会立即转变为海绵

- 类型: `boolean`
- 默认值:
    - 1.14: `false`
    - 1.15+: `true`
- 参考选项: `true`, `false`
- 分类: `ICA`, `FEATURE` 

### 岩浆块伤害物品 (magmaBlockDamageItem)

岩浆块会对物品实体造成伤害

- 类型: `boolean`
- 默认值: `false`  
- 参考选项: `true`, `false`
- 分类: `ICA`, `FEATURE`, `EXPERIMENTAL`

### 功能性海绵物品 (functionalSpongeItem)

海绵的物品实体形态可以吸水，在地狱的物品实体形态的湿海绵会在 60gt 后转换为海绵

- 类型: `boolean`
- 默认值: `false`  
- 参考选项: `true`, `false`
- 分类: `ICA`, `FEATURE`, `EXPERIMENTAL`

### 假人名称前缀检查 (fakePlayerPrefixCheck)

在召唤假人时检查名称前缀

- 类型: `String`
- 默认值: `#none`  
- 参考选项: `#none`, `bot_`
- 分类: `ICA`, `SURVIVAL`, `CREATIVE`

### 假人名称后缀检查 (fakePlayerSuffixCheck)

在召唤假人时检查名称后缀

- 类型: `String`
- 默认值: `#none`  
- 参考选项: `#none`, `_fake`
- 分类: `ICA`, `SURVIVAL`, `CREATIVE`

## 开发

当前主开发分支：**1.15.2**

目前维护的分支：
- 1.14.4，对应 Minecraft 1.14.4
- 1.15.2，对应 Minecraft 1.15.2
- 1.16.4，对应 Minecraft 1.16.4 至 1.16.5
- 1.17，对应 Minecraft 1.17 快照

对于通用的新特性，在 1.15.2 分支中实现，再将其合并至其他分支

分支合并顺序：
- 1.15.2 -> 1.14.4
- 1.15.2 -> 1.16.4 -> 1.17

对于版本专用的修复/补丁，在对应的分支上操作即可

英文文档与中文文档是逐行对应的