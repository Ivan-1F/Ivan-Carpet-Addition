# 命令

[English](rules.md) | **中文**

## xpcounter

`/xpcounter <player>`: 查询一个玩家的计数器

`/xpcounter <player> reset`: 重置一个玩家的计数器

`/log xpcounter <players>`: 订阅计数器

## replaceproperties

`/replaceproperties <from> <to> <property_name> <value> [block_predicate]`

对于一个选区内所有带指定属性的方块，将该属性设置为给定的值

### 参数

 - `<from>`: 选取起点
 - `<to>`: 选取终点
 - `<property_name>`: 属性名，如 `facing`
 - `<value>`: 值，如 `south`
 - `[block_predicate]`: 若指定，只有符合该断言的方块才会被修改

### 例子

```
将 [0, 0, 0] ~ [20, 20, 20] 中的所有带有 facing 属性方块的 facing 属性设为 south
/replaceproperties 0 0 0 20 20 20 facing south

将 [0, 0, 0] ~ [20, 20, 20] 中的所有铁活板门的 facing 属性设为 south
/replaceproperties 0 0 0 20 20 20 facing south minecraft:iron_trapdoor
```
