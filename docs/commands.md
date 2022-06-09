# Commands

**English** | [中文](commands-zh_cn.md)

## xpcounter

`/xpcounter <player>`: query a player's counter

`/xpcounter <player> reset`: reset a player's counter

`/log xpcounter <players>`: subscribe xp counters

## replaceproperties

`/replaceproperties <from> <to> <property_name> <value> [block_predicate]`

For all blocks with the given property in the given range, set the property to the given value

### Arguments

- `<from>`: the start point of the selection
- `<to>`: the end point of the selection
- `<property_name>`: the property name, e.g. `facing`
- `<value>`: the value, e.g. `south`
- `[block_predicate]`: if specified, only blocks that pass the predicate will be modified

### Examples

```
Set the `facing` property for all blocks with the `facing` property within [0, 0, 0] ~ [20, 20, 20] to `south`
/replaceproperties 0 0 0 20 20 20 facing south

Set the `facing` property for all iron trapdoors within [0, 0, 0] ~ [20, 20, 20] to `south`
/replaceproperties 0 0 0 20 20 20 facing south minecraft:iron_trapdoor
```
