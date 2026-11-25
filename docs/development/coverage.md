# API coverage

## 来源与范围

- 上游：[xtermjs/xterm.js](https://github.com/xtermjs/xterm.js)，本地副本 `S:\workspace\github\xtermjs\xterm.js`
- 版本：`@xterm/xterm` 6.0.0（根 `package.json`），类型文件 `typings/xterm.d.ts`
- addon 版本：`fit` 0.11.0、`search` 0.16.0、`serialize` 0.14.0、`unicode11` 0.9.0、`web-links` 0.12.0，
  类型来自各自的 `addons/<name>/typings/*.d.ts`
- 核对日期：2026-11-22
- 纳入统计：`typings/xterm.d.ts` 的公开成员（`class Terminal`、它的 companion、全部 `interface` / `type`），
  加上五个 addon 各自的类与选项类型
- 不纳入：`src/` 内部实现、`@xterm/headless`、`@xterm/website`（本仓库不封装）

上游是 MIT 许可，无商业版本；整个包都纳入范围，没有需要排除的许可条款。

## 统计

| 状态 | 数量 |
|---|---:|
| 总计 | 66 |
| 已完成 | 51 |
| 明确不做 | 15 |
| 待完成 | 0 |

统计口径：`typings/xterm.d.ts` 里能被封装层接触到的公开声明逐条计数（按类型/成员组归并，不是逐字段计数），
加上 15 个 addon 包各计一条。

## 已完成（51）

| # | 上游声明 | Kotlin 侧 | 验证 |
|---|---|---|---|
| 1 | `class Terminal` | `Terminal`（`XtermModule.kt`） | `XtermTest.constructionReadsTheInitOptions` |
| 2 | `Terminal` 构造参数 `ITerminalOptions & ITerminalInitOnlyOptions` | `ITerminalConstructorOptions` + `terminalOptions()` | 同上 |
| 3 | `Terminal.strings`（static） | `Terminal.strings` / `Xterm.strings` | 编译期 |
| 4 | `element` / `screenElement` / `textarea` | 同名属性，可空 | `anUnopenedTerminalHasNoElementOrDimensions` |
| 5 | `rows` / `cols` | 同名属性 | `constructionReadsTheInitOptions` |
| 6 | `buffer`（`IBufferNamespace`） | `IBufferNamespace` | `aFreshBufferStartsAtTheOrigin` |
| 7 | `markers` / `parser` / `unicode` / `modes` | `IMarker[]` / `IParser` / `IUnicodeHandling` / `IModes` | 编译期 |
| 8 | `dimensions` | `IDimensions` | `anUnopenedTerminalHasNoElementOrDimensions` |
| 9 | `options` | 同名属性，可写 | `optionsAreLiveAfterConstruction` |
| 10 | 13 个 `on*` 事件 `IEvent<…>` | `TerminalEvent<T>` + `listen` | `eventSubscriptionsAreDisposables` |
| 11 | `blur` / `focus` / `input` / `resize` / `open` | 同名方法 | demo `PlaygroundPage` |
| 12 | `attachCustomKeyEventHandler` / `attachCustomWheelEventHandler` | 同名，回调类型是 DOM 事件 | 编译期 |
| 13 | `registerLinkProvider` / `registerCharacterJoiner` / `deregisterCharacterJoiner` | 同名 | 编译期 |
| 14 | `registerMarker` / `registerDecoration` | 同名，返回 `IMarker` / `IDecoration` | 编译期 |
| 15 | `hasSelection` / `getSelection` / `getSelectionPosition` | 同名 | `anUnopenedTerminalHasNoElementOrDimensions` |
| 16 | `clearSelection` / `select` / `selectAll` / `selectLines` | 同名 | 编译期 |
| 17 | `dispose` | 同名 | 编译期 |
| 18 | `scrollLines` / `scrollPages` / `scrollToTop` / `scrollToBottom` / `scrollToLine` | 同名 | 编译期 |
| 19 | `clear` / `reset` / `refresh` / `clearTextureAtlas` | 同名 | demo 清屏按钮 |
| 20 | `write` / `writeln` / `paste` | 同名，`write` 带可选回调 | demo 回显 |
| 21 | `loadAddon` | 同名，参数 `ITerminalAddon` | `addonsActivateWithoutATerminalElement` |
| 22 | `interface ITerminalOptions`（约 45 字段） | 同名接口 | `constructionReadsTheInitOptions`、`optionsAreLiveAfterConstruction` |
| 23 | `interface ITerminalInitOnlyOptions` | 同名接口 | `constructionReadsTheInitOptions` |
| 24 | `interface ITheme` | 同名 + `terminalTheme()` | `themeBuildersCarryTheFields` |
| 25 | `interface ITerminalQuirks` | 同名 | 编译期 |
| 26 | `interface IWindowsPty` | 同名 | 编译期 |
| 27 | `interface IVtExtensions` | 同名 | 编译期 |
| 28 | `interface ILogger` | 同名 | 编译期 |
| 29 | `interface IDisposable` | 同名 | `eventSubscriptionsAreDisposables` |
| 30 | `interface IDisposableWithEvent` | 同名 | 编译期 |
| 31 | `interface ITerminalAddon` | 同名 | `addonsActivateWithoutATerminalElement` |
| 32 | `interface IMarker` | 同名 | 编译期 |
| 33 | `interface IDecoration` + `IDecorationOptions` + `IDecorationRenderOptions` | 同名 | 编译期 |
| 34 | `IBufferNamespace` / `IBuffer` / `IBufferLine` / `IBufferCell` | 同名 | `aFreshBufferStartsAtTheOrigin` |
| 35 | `IFunctionIdentifier` / `IParser` | 同名 | 编译期 |
| 36 | `IUnicodeVersionProvider` / `IUnicodeHandling` | 同名 | 编译期 |
| 37 | `IModes` / `IDimensions` / `IOffset` / `IRenderDimensions` | 同名 | 编译期 |
| 38 | 链接相关（`ILinkProvider` / `ILink` / `ILinkDecorations` / `ILinkMatcher` / `ILinkHandler` / `ILinkProviderOptions`） | 同名 | 编译期 |
| 39 | 事件载荷（`IKeyEvent` / `IResizeEvent` / `IRenderRange`） | 同名 | 编译期 |
| 40 | `IEvent<T>`（可调用对象） | `TerminalEvent<T>` / `VoidEvent` + `listen` | `eventSubscriptionsAreDisposables` |
| 41 | `addon-fit`：`FitAddon` | `addon.FitAddon` | `addonsActivateWithoutATerminalElement` |
| 42 | `addon-search`：`SearchAddon` | `addon.SearchAddon` | 编译期 |
| 43 | `addon-serialize`：`SerializeAddon` | `addon.SerializeAddon` | 编译期 |
| 44 | `addon-unicode11`：`Unicode11Addon` | `addon.Unicode11Addon` | 编译期 |
| 45 | `addon-web-links`：`WebLinksAddon` | `addon.WebLinksAddon` | 编译期 |
| 46 | addon 选项类型（`ISearchOptions`、`ISearchAddonOptions`、`ISerializeOptions`、`IWebLinksAddonOptions`…） | 同名接口 | 编译期 |
| 47 | `@xterm/xterm` 的模块形状（`declare module '@xterm/xterm'`） | `@file:JsModule` + `Terminal` 导出 | 全部用例 |
| 48 | 全局 `RegExp` 参数（`ILinkMatcher` 用） | stdlib 的 `kotlin.js.RegExp` | 编译期 |
| 49 | `Terminal` 的 `js` 类型别名 `ITerminalAddon` | 收成 `ITerminalAddon` | 编译期 |
| 50 | demo 侧：`open` + `write` + `FitAddon.fit` 组合 | `PlaygroundPage` | `DemoPageTest` + 手测 |
| 51 | `onData` 回显链路 | `listen` + `write` | `DemoPageTest` |

## 明确不做（15）

| # | addon | 原因 |
|---|---|---|
| 1 | `addon-attach` | 只在把终端挂到已有 `div` 的旧式集成里用，`open()` 已覆盖同一场景 |
| 2 | `addon-clipboard` | 系统剪贴板权限与浏览器策略强耦合，`paste()` 已能写入 |
| 3 | `addon-image` | 需要 `@xterm/addon-image` 的 canvas 渲染管线与图片解码，属于重量级可选能力 |
| 4 | `addon-ligatures` | 字体连字只能靠 canvas 渲染器，与主题/字体设置相互牵制，收益面窄 |
| 5 | `addon-program-status` | 面向 OSC 133 shell 集成，需要应用侧协议支持 |
| 6 | `addon-progress` | 同上，OSC 9;4 进度条，应用侧协议 |
| 7 | `addon-unicode-graphemes` | 与 `unicode11` 二选一，后者更通用 |
| 8 | `addon-web-fonts` | 需要 `document.fonts` 与字体加载策略，交给使用者自备 |
| 9 | `addon-webgl` | WebGL 上下文创建与丢失处理，属于渲染器替换，不是纯声明 |
| 10 | `addon-shell-integration`（不含在 15 个 npm 包内） | 脚本片段分发，不是 JS API |
| 11 | `@xterm/headless` | 服务器端终端，没有 DOM，Kotlin/JS 浏览器目标用不到 |
| 12 | `@xterm/website` | 官网工具链，不是可引用的包 |
| 13 | `src/` 内部模块（`InputHandler`、`BufferSet` 等） | 上游不导出 |
| 14 | `Terminal` 的 `_core` 私有字段 | 上游以 `_` 前缀约定私有 |
| 15 | 字符尺寸测量相关的私有 API | 上游不导出，`dimensions` 已给公开值 |

> 说明：上游 `addons/` 目录下共 15 个 npm 包，本模块封装 5 个（第 41–45 行），其余 10 个按上表逐条给出理由。

## 决策记录

- **`IEvent<T>` 建模成函数类型**：上游把它声明成可调用对象 `(listener) => IDisposable`。Kotlin 侧用
  `typealias VoidEvent = (() -> Unit) -> IDisposable` 与 `TerminalEvent<T> = ((T) -> Unit) -> IDisposable`，
  再加 `listen` 扩展。这样 `terminal.onData.listen { … }` 就是一次普通调用，也不用给每个事件造一个 wrapper 类。
- **构造器交叉类型收成一个接口**：`ITerminalOptions & ITerminalInitOnlyOptions` 在 Kotlin 的 external 世界里没有对应语法，
  声明 `ITerminalConstructorOptions : ITerminalOptions, ITerminalInitOnlyOptions`，构造器只收这一个类型。
- **`@file:JsModule` 文件的边界**：`XtermModule.kt` 里只能放 npm 包的导出（`Terminal` 与它的 companion），
  自己新声明的 external 也塞不进同一个文件（编译报 `Only external declarations are allowed`）。
  但 `RegExp` 不用自己声明：stdlib 的 `kotlin.js.RegExp` 就是全局构造器，
  `addon/WebLinksAddonModule.kt` 里 `import kotlin.js.RegExp` 直接当参数类型用。
- **解析器回调用 `JsAny`**：上游允许 handler 返回 `Boolean` 或 `Promise<Boolean>`，Kotlin 无法用一个签名表达两者，
  在 `IParser` 处收成 `JsAny`，由调用方自己按需 cast。
- **不写「write 之后读 buffer」的测试**：xterm 的 `write` 是异步的（解析在下一帧），kotlin.test 没有 await，
  断言必然不稳定。缓冲区用例只断言新终端的状态，异步链路交给 demo 的 e2e（`waitForFunction` 等真实渲染）。
- **addon 只做 5 个**：见上表。判据是「纯声明 + 一次 `loadAddon` 就能用」，凡是需要渲染器替换、应用侧协议或
  浏览器权限的能力都不纳入，避免把一份 npm 包变成一套运行时行为。
- **版本口径**：`6.0.0-kt.1`；addon 版本跟 `@xterm/xterm` 一起在根 `package.json` 里声明，模块 `build.gradle.kts`
  用 `npm(...)` 逐条引入，改任何一条都要跑 `kotlinUpgradePackageLock`。
