# TODO

## 上游 API 覆盖

把 `@xterm/xterm` 6.0.0 的公开声明（`typings/xterm.d.ts`）与五个 addon 的声明映射成 Kotlin 类型，并在浏览器里跑通。

- 总计：66
- 已完成：51
- 明确不做：15
- 待完成：0

详细清单：[coverage.md](docs/development/coverage.md)

### 当前待办

- [ ] 无。下一批事项见本文件的「项目交付」小节。

## 项目交付

- [x] 模块 `ktjs-xterm`：`XtermModule.kt` / `XtermTypes.kt` / `Xterm.kt` / 5 个 addon 模块 / `JsRegExp.kt`
- [x] jsTest：7 个用例，`:ktjs-xterm:jsBrowserTest` 通过
- [x] demo：overview / playground / api / conventions，中英双语，接 `ktjs-dom-kit` 与 `ktjs-vaadin`
- [x] docs：intro / getting-started / usage / licensing，中英双语，kit 首页五件套、右侧 TOC 可折叠
- [x] `pagesSite` 与 Pages 工作流（push 到 main 自动发布）
- [x] MIT 许可 + `@xterm/xterm` 与 addon 的 MIT 声明（[guide/licensing](../docs/src/jsMain/resources/docs/guide/licensing.md)）
- [x] `.agents/skills/` 同步（模板副本）
- [ ] GitHub 仓库创建与首次推送
- [ ] Pages 打开（`gh api -X POST repos/shijianjs/ktjs-xterm/pages`）

### 注意事项

- 声明只放 `jsMain`：`external interface` + sealed 字面量，不写 `dynamic`。
- `@file:JsModule` 的文件里只能有 npm 包的导出；全局 `RegExp` 放旁边的 `addon/JsRegExp.kt`。
- 事件是函数类型：`terminal.onData.listen { … }`，返回 `IDisposable`。
- 加 npm 依赖要同时改根 `package.json` 与模块 `build.gradle.kts` 的 `npm(...)`，然后跑 `kotlinUpgradePackageLock`。
- 版本口径：`<npm 版本>-kt.<修订号>`，工作树写 `-SNAPSHOT`。
