## Ivan Carpet Addition

[![License](https://img.shields.io/github/license/Ivan-YFw/Ivan-Carpet-Addition.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/Ivan-YFw/Ivan-Carpet-Addition.svg)](https://github.com/Ivan-YFw/Ivan-Carpet-Addition/issues)

[English](https://github.com/Ivan-YFw/Ivan-Carpet-Addition/blob/fabric-1.15.2/README.md) / **中文**

这是一个 [Carpet mod](https://github.com/gnembon/fabric-carpet) (fabric-carpet) 的扩展 mod，包含了不少~~NotVanilla的~~有意思的功能以及特性

跟同 Minecraft 版本的 carpet mod 一起使用即可。尽可能地使用较新的 carpet mod

## 索引

### [规则](#规则列表)

 - [自定义版本](#自定义版本-customVersion)
 - [ICA同步协议](#ICA同步协议-icaSyncProtocol)
 - [蜘蛛网减缓实体的速度](#蜘蛛网粘性-cobwebSlowDownSpeed)
 - [假人预设](#假人预设-fakePlayerPreset)
 - [player指令不准控制自己](#player指令不准控制自己-playerCommandNoControlSelf)
 - [仙人掌扳手音效](#仙人掌扳手音效-flippinCactusSound)
 - [可编辑告示牌](#可编辑告示牌-editableSign)

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