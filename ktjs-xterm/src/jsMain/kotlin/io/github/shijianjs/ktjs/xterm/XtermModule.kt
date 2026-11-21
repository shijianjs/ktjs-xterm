@file:JsModule("@xterm/xterm")

package io.github.shijianjs.ktjs.xterm

import js.buffer.ArrayBuffer
import js.typedarrays.Uint8Array
import kotlin.js.definedExternally
import web.html.HTMLElement
import web.html.HTMLTextAreaElement
import web.keyboard.KeyboardEvent
import web.mouse.WheelEvent

/**
 * The terminal, as the `@xterm/xterm` module exports it.
 *
 * The class is the only runtime export of the package; every type it mentions is declared in
 * `XtermTypes.kt`. Events are modelled as functions (`IEvent<T>` is a callable object), see
 * [TerminalEvent] there.
 */
external class Terminal(options: ITerminalConstructorOptions? = definedExternally) : IDisposable {
    companion object {
        /** The prompt and "too much output" strings the terminal shows. */
        val strings: ILocalizableStrings
    }

    /** The element containing the terminal. */
    val element: HTMLElement?

    /** The screen element holding the render layers and decorations, without viewport and scrollbar. */
    val screenElement: HTMLElement?

    /** The textarea that accepts input for the terminal. */
    val textarea: HTMLTextAreaElement?

    /** Rows in the viewport; set them through `cols`/`rows` at construction or [resize]. */
    val rows: Int

    /** Columns in the viewport; set them through `cols`/`rows` at construction or [resize]. */
    val cols: Int

    /** The normal and alternate buffers. */
    val buffer: IBufferNamespace

    /** Every marker registered against the active buffer. */
    val markers: Array<IMarker>

    /** The escape-sequence parser, for custom handlers. */
    val parser: IParser

    /** (Experimental) Unicode version handling. */
    val unicode: IUnicodeHandling

    /** The modes set by SM/DECSET. */
    val modes: IModes

    /** Render dimensions; `null` until [open] has run. */
    val dimensions: IRenderDimensions?

    /** The live options object. Assigning to a field re-applies it in place. */
    var options: ITerminalOptions

    val onBell: VoidEvent
    val onBinary: TerminalEvent<String>
    val onCursorMove: VoidEvent
    val onData: TerminalEvent<String>
    val onKey: TerminalEvent<IKeyEvent>
    val onLineFeed: VoidEvent
    val onRender: TerminalEvent<IRenderRange>
    val onWriteParsed: VoidEvent
    val onResize: TerminalEvent<IResizeEvent>
    val onScroll: TerminalEvent<Int>
    val onSelectionChange: VoidEvent
    val onTitleChange: TerminalEvent<String>
    val onDimensionsChange: TerminalEvent<IRenderDimensions>

    fun blur()

    fun focus()

    /** Feeds input as if it came from the user. */
    fun input(data: String, wasUserInput: Boolean = definedExternally)

    /** Changes the viewport size in cells. */
    fun resize(columns: Int, rows: Int)

    /** Renders the terminal into [parent]; the element must be attached and sized. */
    fun open(parent: HTMLElement)

    /** Returning `false` stops xterm from handling the key. */
    fun attachCustomKeyEventHandler(customKeyEventHandler: (KeyboardEvent) -> Boolean)

    /** Returning `false` stops xterm from scrolling. */
    fun attachCustomWheelEventHandler(customWheelEventHandler: (WheelEvent) -> Boolean)

    fun registerLinkProvider(linkProvider: ILinkProvider): IDisposable

    /** Returns the id needed to [deregisterCharacterJoiner]. */
    fun registerCharacterJoiner(handler: (String) -> Array<Array<Int>>): Int

    fun deregisterCharacterJoiner(joinerId: Int)

    /** Marks a line, offset from the cursor. */
    fun registerMarker(cursorYOffset: Int = definedExternally): IMarker

    fun registerDecoration(decorationOptions: IDecorationOptions): IDecoration?

    fun hasSelection(): Boolean

    fun getSelection(): String

    fun getSelectionPosition(): IBufferRange?

    fun clearSelection()

    fun select(column: Int, row: Int, length: Int)

    fun selectAll()

    fun selectLines(start: Int, end: Int)

    fun scrollLines(amount: Int)

    fun scrollPages(pageCount: Int)

    fun scrollToTop()

    fun scrollToBottom()

    fun scrollToLine(line: Int)

    /** Clears the viewport and keeps the scrollback. */
    fun clear()

    fun write(data: String, callback: (() -> Unit)? = definedExternally)

    fun write(data: Uint8Array<ArrayBuffer>, callback: (() -> Unit)? = definedExternally)

    fun writeln(data: String, callback: (() -> Unit)? = definedExternally)

    fun writeln(data: Uint8Array<ArrayBuffer>, callback: (() -> Unit)? = definedExternally)

    /** Pastes text, honouring bracketed paste mode. */
    fun paste(data: String)

    fun refresh(start: Int, end: Int)

    fun clearTextureAtlas()

    /** Resets the terminal to its initial state. */
    fun reset()

    fun loadAddon(addon: ITerminalAddon)

    override fun dispose()
}
