@file:JsModule("@xterm/addon-unicode11")

package io.github.shijianjs.ktjs.xterm.addon

import io.github.shijianjs.ktjs.xterm.ITerminalAddon
import io.github.shijianjs.ktjs.xterm.Terminal

/**
 * Registers the Unicode 11 width table.
 *
 * The addon only registers the provider; the terminal still has to select it with
 * `terminal.unicode.activeVersion = "11"`.
 */
external class Unicode11Addon : ITerminalAddon {
    constructor()

    override fun activate(terminal: Terminal)

    override fun dispose()
}
