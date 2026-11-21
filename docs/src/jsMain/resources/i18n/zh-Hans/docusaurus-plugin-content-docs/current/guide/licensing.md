---
sidebar_position: 3
---

# 许可证

## 封装层本身

`ktjs-xterm` 采用 MIT 许可证，见 [LICENSE](https://github.com/shijianjs/ktjs-xterm/blob/main/LICENSE)。

## 上游

`@xterm/xterm` 与本模块封装的每个 addon 都是 MIT：

- `@xterm/xterm` 6.0.0 — MIT，© The xterm.js authors
- `@xterm/addon-fit` 0.11.0、`@xterm/addon-search` 0.16.0、`@xterm/addon-serialize` 0.14.0、
  `@xterm/addon-unicode11` 0.9.0、`@xterm/addon-web-links` 0.12.0 — MIT，同一版权主体

它们的条款随各自的 npm 包走，而这些包仍是本模块的依赖；这里不改变它们的许可。

## 使用者需要做什么

| 问题 | 回答 |
| --- | --- |
| 能用在闭源产品里吗？ | 可以，MIT 允许。保留上游版权声明即可，打包进来的 npm 包本身带着。 |
| 必须公开自己的源码吗？ | 不必。 |
| addon 是另一套许可吗？ | 名义上分开，实际同为 MIT；本模块封装的部分没有商业版。 |

上游仓库里还有 `@xterm/headless` 与站点工具等本模块未触及的产物，它们的许可不属于本模块范围。
