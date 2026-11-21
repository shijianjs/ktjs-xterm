package io.github.shijianjs.ktjs.xterm

import js.objects.unsafeJso
import kotlin.js.JsAny
import kotlin.js.definedExternally
import web.html.HTMLElement
import web.keyboard.KeyboardEvent
import web.mouse.MouseEvent
import web.mouse.WheelEvent

// ------------------------------------------------------------------------------------------------
// Events
// ------------------------------------------------------------------------------------------------

/**
 * `IEvent<void>`: a callable object that takes a listener and returns a subscription.
 *
 * In JavaScript the event itself is a function, `terminal.onBell(listener)`. A Kotlin function type
 * models that exactly; [listen] is the sugar that makes it read like an event.
 */
typealias VoidEvent = (() -> Unit) -> IDisposable

/** `IEvent<T>`: same shape as [VoidEvent], with one listener argument. */
typealias TerminalEvent<T> = ((T) -> Unit) -> IDisposable

/** Subscribes to a payloadless event. */
fun VoidEvent.listen(handler: () -> Unit): IDisposable = this(handler)

/** Subscribes to an event that carries a payload. */
fun <T> TerminalEvent<T>.listen(handler: (T) -> Unit): IDisposable = this(handler)

// ------------------------------------------------------------------------------------------------
// Options
// ------------------------------------------------------------------------------------------------

/** What the terminal's `onKey` listener receives. */
external interface IKeyEvent {
    var key: String
    var domEvent: KeyboardEvent
}

/** What `onRender` receives. */
external interface IRenderRange {
    var start: Int
    var end: Int
}

/** What `onResize` receives. */
external interface IResizeEvent {
    var cols: Int
    var rows: Int
}

/**
 * Every option the terminal reads.
 *
 * Every field is optional and nullable: omitting one means "upstream default". Field names keep the
 * upstream spelling because they are keys of the object xterm reads.
 */
external interface ITerminalOptions {
    var allowProposedApi: Boolean?
    var allowTransparency: Boolean?
    var altClickMovesCursor: Boolean?
    var convertEol: Boolean?
    var cursorBlink: Boolean?
    var blinkIntervalDuration: Double?
    var cursorStyle: String?
    var cursorWidth: Double?
    var cursorInactiveStyle: String?
    var disableStdin: Boolean?
    var documentOverride: JsAny?
    var drawBoldTextInBrightColors: Boolean?
    var fastScrollSensitivity: Double?
    var fontSize: Double?
    var fontFamily: String?
    var fontWeight: String?
    var fontWeightBold: String?
    var ignoreBracketedPasteMode: Boolean?
    var letterSpacing: Double?
    var lineHeight: Double?
    var linkHandler: ILinkHandler?
    var logLevel: String?
    var logger: ILogger?
    var macOptionIsMeta: Boolean?
    var macOptionClickForcesSelection: Boolean?
    var minimumContrastRatio: Double?
    var mouseEventsRequireAlt: Boolean?
    var quirks: ITerminalQuirks?
    var reflowCursorLine: Boolean?
    var rescaleOverlappingGlyphs: Boolean?
    var rightClickSelectsWord: Boolean?
    var screenReaderMode: Boolean?
    var scrollback: Int?
    var scrollOnEraseInDisplay: Boolean?
    var scrollOnUserInput: Boolean?
    var scrollSensitivity: Double?
    var scrollbar: IScrollbarOptions?
    var smoothScrollDuration: Double?
    var tabStopWidth: Int?
    var theme: ITheme?
    var vtExtensions: IVtExtensions?
    var windowsPty: IWindowsPty?
    var wordSeparator: String?
    var windowOptions: IWindowOptions?
}

/** Options that only the constructor honours. */
external interface ITerminalInitOnlyOptions {
    var cols: Int?
    var rows: Int?
    var showCursorImmediately: Boolean?
}

/**
 * The single object the constructor takes.
 *
 * Upstream declares the constructor parameter as `ITerminalOptions & ITerminalInitOnlyOptions` — one
 * object carrying both sets of keys — so the wrapper declares the intersection as this interface.
 */
external interface ITerminalConstructorOptions : ITerminalOptions, ITerminalInitOnlyOptions

/** The colours of the viewport, the cursor, the selection and the 16 ANSI colours. */
external interface ITheme {
    var foreground: String?
    var background: String?
    var cursor: String?
    var cursorAccent: String?
    var selectionBackground: String?
    var selectionForeground: String?
    var selectionInactiveBackground: String?
    var scrollbarSliderBackground: String?
    var scrollbarSliderHoverBackground: String?
    var scrollbarSliderActiveBackground: String?
    var overviewRulerBorder: String?
    var black: String?
    var red: String?
    var green: String?
    var yellow: String?
    var blue: String?
    var magenta: String?
    var cyan: String?
    var white: String?
    var brightBlack: String?
    var brightRed: String?
    var brightGreen: String?
    var brightYellow: String?
    var brightBlue: String?
    var brightMagenta: String?
    var brightCyan: String?
    var brightWhite: String?
    var extendedAnsi: Array<String>?
}

external interface ITerminalQuirks {
    var allowSetCursorBlink: Boolean?
}

/** VT extensions that are off by default. */
external interface IVtExtensions {
    var kittyKeyboard: Boolean?
    var kittySgrBoldFaintControl: Boolean?
    var win32InputMode: Boolean?
    var colorSchemeQuery: Boolean?
}

/** Tells the terminal which Windows PTY it is talking to. */
external interface IWindowsPty {
    var backend: String?
    var buildNumber: Int?
}

external interface ILogger {
    fun trace(message: String, vararg args: JsAny?)
    fun debug(message: String, vararg args: JsAny?)
    fun info(message: String, vararg args: JsAny?)
    fun warn(message: String, vararg args: JsAny?)
    fun error(message: JsAny, vararg args: JsAny?)
}

external interface IScrollbarOptions {
    var showScrollbar: Boolean?
    var showArrows: Boolean?
    var width: Double?
    var overviewRuler: IOverviewRulerOptions?
}

external interface IOverviewRulerOptions {
    var showTopBorder: Boolean?
    var showBottomBorder: Boolean?
}

/** DECSET 1xx window-title and window-manipulation sequences, each off unless enabled. */
external interface IWindowOptions {
    var restoreWin: Boolean?
    var minimizeWin: Boolean?
    var setWinPosition: Boolean?
    var setWinSizePixels: Boolean?
    var raiseWin: Boolean?
    var lowerWin: Boolean?
    var refreshWin: Boolean?
    var setWinSizeChars: Boolean?
    var maximizeWin: Boolean?
    var fullscreenWin: Boolean?
    var getWinState: Boolean?
    var getWinPosition: Boolean?
    var getWinSizePixels: Boolean?
    var getScreenSizePixels: Boolean?
    var getCellSizePixels: Boolean?
    var getWinSizeChars: Boolean?
    var getScreenSizeChars: Boolean?
    var getIconTitle: Boolean?
    var getWinTitle: Boolean?
    var pushTitle: Boolean?
    var popTitle: Boolean?
    var setWinLines: Boolean?
}

external interface ILocalizableStrings {
    var promptLabel: String
    var tooMuchOutput: String
}

// ------------------------------------------------------------------------------------------------
// Lifetime
// ------------------------------------------------------------------------------------------------

external interface IDisposable {
    fun dispose()
}

external interface IDisposableWithEvent : IDisposable {
    val onDispose: VoidEvent
    val isDisposed: Boolean
}

// ------------------------------------------------------------------------------------------------
// Addons
// ------------------------------------------------------------------------------------------------

/** An addon, as [Terminal.loadAddon] takes it. */
external interface ITerminalAddon : IDisposable {
    fun activate(terminal: Terminal)
}

// ------------------------------------------------------------------------------------------------
// Markers and decorations
// ------------------------------------------------------------------------------------------------

external interface IMarker : IDisposableWithEvent {
    val id: Int
    val line: Int
}

external interface IDecoration : IDisposableWithEvent {
    val marker: IMarker
    val onRender: TerminalEvent<HTMLElement?>
    var element: HTMLElement?
    val options: IDecorationRenderOptions
}

/** The ruler tick an overview-ruler decoration draws. */
external interface IDecorationOverviewRulerOptions {
    var color: String
    var position: String?
}

/** The current value of [IDecoration.options], narrowed to what upstream exposes there. */
external interface IDecorationRenderOptions {
    var overviewRulerOptions: IDecorationOverviewRulerOptions?
}

external interface IDecorationOptions {
    val marker: IMarker
    val anchor: String?
    val x: Int?
    val width: Int?
    val height: Int?
    val backgroundColor: String?
    val foregroundColor: String?
    val layer: String?
    var overviewRulerOptions: IDecorationOverviewRulerOptions?
}

// ------------------------------------------------------------------------------------------------
// Links
// ------------------------------------------------------------------------------------------------

external interface IBufferRange {
    var start: IBufferCellPosition
    var end: IBufferCellPosition
}

external interface IBufferCellPosition {
    var x: Int
    var y: Int
}

external interface IViewportRange {
    var start: IViewportRangePosition
    var end: IViewportRangePosition
}

external interface IViewportRangePosition {
    var x: Int
    var y: Int
}

external interface ILinkHandler {
    fun activate(event: MouseEvent, text: String, range: IBufferRange)
    fun hover(event: MouseEvent, text: String, range: IBufferRange)
    fun leave(event: MouseEvent, text: String)
    var allowNonHttpProtocols: Boolean?
}

external interface ILinkProvider {
    fun provideLinks(bufferLineNumber: Int, callback: (Array<ILink>?) -> Unit)
}

external interface ILink {
    var range: IBufferRange
    var text: String
    var decorations: ILinkDecorations?
    fun activate(event: MouseEvent, text: String)
    fun hover(event: MouseEvent, text: String)
    fun leave(event: MouseEvent, text: String)
    fun dispose()
}

external interface ILinkDecorations {
    var pointerCursor: Boolean
    var underline: Boolean
}

/** An element supplier for `registerBufferElementProvider`-style integrations. */
external interface IBufferElementProvider {
    fun provideBufferElements(): JsAny
}

// ------------------------------------------------------------------------------------------------
// Buffers
// ------------------------------------------------------------------------------------------------

external interface IBufferNamespace {
    val active: IBuffer
    val normal: IBuffer
    val alternate: IBuffer
    val onBufferChange: TerminalEvent<IBuffer>
}

external interface IBuffer {
    /** `normal` or `alternate`. */
    val type: String
    val cursorY: Int
    val cursorX: Int
    val viewportY: Int
    val baseY: Int
    val length: Int

    fun getLine(y: Int): IBufferLine?

    fun getNullCell(): IBufferCell
}

external interface IBufferLine {
    val isWrapped: Boolean
    val length: Int

    fun getCell(x: Int, cell: IBufferCell? = definedExternally): IBufferCell?

    fun translateToString(trimRight: Boolean? = definedExternally, startColumn: Int? = definedExternally, endColumn: Int? = definedExternally): String
}

external interface IBufferCell {
    fun getWidth(): Int
    fun getChars(): String
    fun getCode(): Int
    fun getFgColorMode(): Int
    fun getBgColorMode(): Int
    fun getFgColor(): Int
    fun getBgColor(): Int
    fun isBold(): Int
    fun isItalic(): Int
    fun isDim(): Int
    fun isUnderline(): Int
    fun isBlink(): Int
    fun isInverse(): Int
    fun isInvisible(): Int
    fun isStrikethrough(): Int
    fun isOverline(): Int
    fun isFgRGB(): Boolean
    fun isBgRGB(): Boolean
    fun isFgPalette(): Boolean
    fun isBgPalette(): Boolean
    fun isFgDefault(): Boolean
    fun isBgDefault(): Boolean
    fun isAttributeDefault(): Boolean
    fun getUnderlineStyle(): Int
    fun getUnderlineColor(): Int
    fun getUnderlineColorMode(): Int
    fun isUnderlineColorRGB(): Boolean
    fun isUnderlineColorPalette(): Boolean
    fun isUnderlineColorDefault(): Boolean
    fun attributesEquals(other: IBufferCell): Boolean
}

// ------------------------------------------------------------------------------------------------
// Parser
// ------------------------------------------------------------------------------------------------

external interface IFunctionIdentifier {
    var prefix: String?
    var intermediates: String?
    var final: String
}

external interface IParser {
    fun registerCsiHandler(id: IFunctionIdentifier, callback: (Array<JsAny>) -> JsAny): IDisposable
    fun registerDcsHandler(id: IFunctionIdentifier, callback: (String, Array<JsAny>) -> JsAny): IDisposable
    fun registerEscHandler(id: IFunctionIdentifier, handler: () -> JsAny): IDisposable
    fun registerOscHandler(ident: Int, callback: (String) -> JsAny): IDisposable
    fun registerApcHandler(id: IFunctionIdentifier, callback: (String) -> JsAny): IDisposable
}

// ------------------------------------------------------------------------------------------------
// Unicode
// ------------------------------------------------------------------------------------------------

external interface IUnicodeVersionProvider {
    val version: String
    fun wcwidth(codepoint: Int): Int
    fun charProperties(codepoint: Int, preceding: Int): Int
}

external interface IUnicodeHandling {
    fun register(provider: IUnicodeVersionProvider)
    val versions: Array<String>
    var activeVersion: String
}

// ------------------------------------------------------------------------------------------------
// Modes and dimensions
// ------------------------------------------------------------------------------------------------

external interface IModes {
    val applicationCursorKeysMode: Boolean
    val applicationKeypadMode: Boolean
    val bracketedPasteMode: Boolean
    val insertMode: Boolean
    val mouseTrackingMode: String
    val originMode: Boolean
    val reverseWraparoundMode: Boolean
    val sendFocusMode: Boolean
    val showCursor: Boolean
    val synchronizedOutputMode: Boolean
    val win32InputMode: Boolean
    val wraparoundMode: Boolean
}

external interface IDimensions {
    var width: Double
    var height: Double
}

external interface IOffset {
    var top: Double
    var left: Double
}

external interface IRenderDimensions {
    var css: IRenderDimensionsCss
    var device: IRenderDimensionsDevice
}

external interface IRenderDimensionsCss {
    var canvas: IDimensions
    var cell: IDimensions
}

external interface IRenderDimensionsDevice {
    var canvas: IDimensions
    var cell: IDimensions
    var char: IRenderDimensionsChar
}

external interface IRenderDimensionsChar {
    var width: Double
    var height: Double
    var top: Double
    var left: Double
}

// ------------------------------------------------------------------------------------------------
// Builders
// ------------------------------------------------------------------------------------------------

/** Builds a constructor options object field by field. */
fun terminalOptions(build: ITerminalConstructorOptions.() -> Unit): ITerminalConstructorOptions = unsafeJso(build)

/** Builds a theme object field by field. */
fun terminalTheme(build: ITheme.() -> Unit): ITheme = unsafeJso(build)
