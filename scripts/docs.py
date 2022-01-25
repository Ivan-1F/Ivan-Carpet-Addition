import itertools
import json
import re

SETTINGS_PATH = '../src/main/java/me/ivan/ivancarpetaddition/IvanCarpetAdditionSettings.java'
CN_LANG_FILE_PATH = '../src/main/resources/assets/ivancarpetaddition/lang/zh_cn.json'

HEADER = '''## Ivan Carpet Addition

[![License](https://img.shields.io/github/license/Ivan-1F/Ivan-Carpet-Addition.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/Ivan-1F/Ivan-Carpet-Addition.svg)](https://github.com/Ivan-1F/Ivan-Carpet-Addition/issues)
[![MC Versions](http://cf.way2muchnoise.eu/versions/For%20MC_ivan-carpet-addition_all.svg)](https://www.curseforge.com/minecraft/mc-mods/ivan-carpet-addition)
[![CurseForge](http://cf.way2muchnoise.eu/full_ivan-carpet-addition_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/ivan-carpet-addition)

**English** / [中文](https://github.com/Ivan-1F/Ivan-Carpet-Addition/blob/fabric-1.15.2/README_CN.md)

A [Carpet mod](https://github.com/gnembon/fabric-carpet) (fabric-carpet) extension, a collection of carpet mod style useful tools and interesting features.

Use with carpet mod in the same Minecraft version. Use newer carpet mod versions whenever possible

## Index

### [Rules](#rule-list)

{0} 
### Others

 - [development](#Development)
 
## Rule List
'''

FOOTER = '''## Development

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

The English doc and the Chinese doc are aligned line by line btw'''

HEADER_CN = '''## Ivan Carpet Addition

[![License](https://img.shields.io/github/license/Ivan-1F/Ivan-Carpet-Addition.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/Ivan-1F/Ivan-Carpet-Addition.svg)](https://github.com/Ivan-1F/Ivan-Carpet-Addition/issues)
[![MC Versions](http://cf.way2muchnoise.eu/versions/For%20MC_ivan-carpet-addition_all.svg)](https://www.curseforge.com/minecraft/mc-mods/ivan-carpet-addition)
[![CurseForge](http://cf.way2muchnoise.eu/full_ivan-carpet-addition_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/ivan-carpet-addition)

[English](https://github.com/Ivan-1F/Ivan-Carpet-Addition/blob/fabric-1.15.2/README.md) / **中文**

这是一个 [Carpet mod](https://github.com/gnembon/fabric-carpet) (fabric-carpet) 的扩展 mod，包含了不少~~NotVanilla的~~有意思的功能以及特性

跟同 Minecraft 版本的 carpet mod 一起使用即可。尽可能地使用较新的 carpet mod

## 索引

### [规则](#规则列表)

{0}
### 其他

 - [开发](#开发)

## 规则列表
'''

FOOTER_CN = '''## 开发

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

英文文档与中文文档是逐行对应的'''

body_en = ''
body_cn = ''
catalogue_en = ''
catalogue_cn = ''

if __name__ == '__main__':
    with open(SETTINGS_PATH) as settings_file, open(CN_LANG_FILE_PATH) as cn_lang_file:
        ctx = settings_file.read()
        cn_lang = json.load(cn_lang_file)
        lines = [line.strip() for line in ctx.splitlines()]
        first_rule_index = lines.index('// RULE BEGIN')
        last_rule_index = lines.index('// RULE END')
        lines = lines[first_rule_index + 2:last_rule_index - 1]

        rules = ['\n'.join(list(item)) for k, item in itertools.groupby(lines, lambda x: x != '') if k]
        for rule in rules:
            result = re.search(r'public static (.*?) (.*?) = (.*?);', rule)
            _type = f'`{result.group(1)}`'
            name = result.group(2)
            default_value = result.group(3).replace('"', '')
            default_value = f'`{default_value}`'

            result = re.search(r'desc = "(.*?)"', rule)
            desc = result.group(1)

            result = re.search(r'category = {(.*?)}', rule)
            categories = result.group(1)
            categories = categories.split(', ')
            categories_formatted = categories.__str__()[1:-1].replace("'", "`")

            result = re.search(r'extra = {(.*?)}', rule)
            extras = []
            if result and '// DOCS IGNORE extra' not in rule:
                extras = eval(f'[{result.group(1)}]')

            suggested_options: str = '`true`, `false`'
            result = re.search(r'options = {(.*?)}', rule)
            if result:
                suggested_options = eval(f'[{result.group(1)}]')
                suggested_options = suggested_options.__str__()[1:-1].replace("'", '`')

            result = re.search(r'// DOCS MODIFY (.*?): (.*)', rule)
            if result:
                target = result.group(1)
                value = result.group(2)
                cmd = f'{target} = {value}'
                exec(cmd)

            result = re.search(r'// DOCS APPEND (.*)', rule)
            addition = ''
            if result:
                addition = result.group(1)
                addition += '\n'

            body_en += f'### {name}\n'
            body_en += '\n'
            body_en += desc + '\n'
            body_en += '\n'
            for extra in extras:
                body_en += extra.replace("'", '`') + '\n'
                body_en += '\n'
            body_en += f' - Type: {_type}\n'
            body_en += f' - Default value: {default_value}\n'
            body_en += f' - Suggested options: {suggested_options}\n'
            body_en += f' - Categories: {categories_formatted}\n'
            body_en += '\n'
            body_en += addition
            body_en += '\n'

            catalogue_en += f' - [{name}](#{name})\n'

            body_cn += f'### {cn_lang[f"rule.{name}.name"]} ({name})\n'
            body_cn += '\n'
            body_cn += cn_lang[f"rule.{name}.desc"] + '\n'
            body_cn += '\n'
            for index, extra in enumerate(extras):
                body_cn += cn_lang[f"rule.{name}.extra.{index}"].replace("'", '`') + '\n'
                body_cn += '\n'
            body_cn += f' - 类型: {_type}\n'
            body_cn += f' - 默认值: {default_value}\n'
            body_cn += f' - 参考选项: {suggested_options}\n'
            body_cn += f' - 分类: {categories_formatted}\n'
            body_cn += '\n'
            body_cn += addition
            body_cn += '\n'

            catalogue_cn += f' - [{cn_lang[f"rule.{name}.name"]}](#{cn_lang[f"rule.{name}.name"]}-{name})\n'

    with open('../generated/README.md', 'w+') as f:
        f.write('{}\n{}{}'.format(HEADER.format(catalogue_en), body_en, FOOTER))

    with open('../generated/README_CN.md', 'w+') as f:
        f.write('{}\n{}{}'.format(HEADER_CN.format(catalogue_cn), body_cn, FOOTER_CN))

    print('Done! README.md and README_CN.md have been generated')
