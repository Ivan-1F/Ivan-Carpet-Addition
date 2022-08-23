# 记录器

[English](loggers.md) | **中文**

## xpcounter

`/log xpcounter <玩家名>`

这是一个 HUD 记录器

类似 carpet 的漏斗计数器对应的 `counter` 记录器，该记录器用于展示玩家吸收的经验数量

属性:
- 默认选项: N/A
- 参考选项: 所有在线的玩家名

## shulker

`/log shulker <选项列表>`

当选项包含 `teleport` 时，记录潜影贝的传送行为

当选项包含 `dupe` 时，记录潜影贝的复制行为，生成新潜影贝后的传送坐标也会被记录，仅在 1.17 以上有效

属性:
- 默认选项: `teleport`
- 参考选项: `teleport`, `dupe`, `teleport,dupe`
