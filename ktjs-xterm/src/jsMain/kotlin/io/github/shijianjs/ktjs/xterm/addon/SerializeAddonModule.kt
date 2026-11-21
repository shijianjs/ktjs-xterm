@file:JsModule("@xterm/addon-serialize")

package io.github.shijianjs.ktjs.xterm.addon

import io.github.shijianjs.ktjs.xterm.IBufferRange
import io.github.shijianjs.ktjs.xterm.IMarker
import io.github.shijianjs.ktjs.xterm.ITerminalAddon
import io.github.shijianjs.ktjs.xterm.Terminal
import kotlin.js.JsAny
import kotlin.js.definedExternally

/** Writes the terminal's content back out as an escape-sequence stream, or as HTML. */
external class SerializeAddon : ITerminalAddon {
    constructor()

    override fun activate(terminal: Terminal)

    override fun dispose()

    fun serialize(options: ISerializeOptions? = definedExternally): String

    fun serializeAsHTML(options: ISerializeHTMLOptions? = definedExternally): String
}

external interface ISerializeOptions {
    var range: ISerializeRange?
    var scrollback: Int?
    var excludeModes: Boolean?
    var excludeAltBuffer: Boolean?
}

/** The lines to serialize, either as markers or as plain line numbers. */
external interface ISerializeRange {
    var start: JsAny
    var end: JsAny
}

external interface ISerializeHTMLOptions {
    var scrollback: Int?
    var onlySelection: Boolean?
    var includeGlobalBackground: Boolean?
    var range: ISerializeBufferRange?
}

external interface ISerializeBufferRange {
    var startLine: Int
    var endLine: Int
    var startCol: Int
}

/** The same range upstream also accepts from the terminal's own `IBufferRange`. */
external interface ISerializeMarkerRange {
    var start: IMarker
    var end: IMarker
}

/** The buffer-based range, kept for callers that already hold one. */
external interface ISerializeBufferRangeView {
    var range: IBufferRange?
}
