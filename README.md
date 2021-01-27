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
 
## Rule List

### customVersion

Sets a different version on client trying to connect to the server

use '_' to disable

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

Overwrite the speed the cobweb slow down entities.

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