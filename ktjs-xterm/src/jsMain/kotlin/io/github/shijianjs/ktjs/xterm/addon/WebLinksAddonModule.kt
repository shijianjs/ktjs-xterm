@file:JsModule("@xterm/addon-web-links")

package io.github.shijianjs.ktjs.xterm.addon

import io.github.shijianjs.ktjs.xterm.ITerminalAddon
import io.github.shijianjs.ktjs.xterm.IViewportRange
import kotlin.js.definedExternally
import web.mouse.MouseEvent

/**
 * Turns URLs in the terminal output into clickable links.
 *
 * With no handler a click opens the URL in a new tab; pass [handler] to intercept the click, and
 * [options] to supply `hover` / `leave` callbacks or a custom URL pattern.
 */
external class WebLinksAddon : ITerminalAddon {
    constructor(
        handler: ((MouseEvent, String) -> Unit)? = definedExternally,
        options: IWebLinksProviderOptions? = definedExternally,
    )

    override fun activate(terminal: io.github.shijianjs.ktjs.xterm.Terminal)

    override fun dispose()
}

/** Per-link callbacks and the pattern that decides what counts as a URL. */
external interface IWebLinksProviderOptions {
    var hover: ((MouseEvent, String, IViewportRange) -> Unit)?
    var leave: ((MouseEvent, String) -> Unit)?
    var urlRegex: JsRegExp?
}
