package io.github.shijianjs.ktjs.xterm

/**
 * The wrapper's front door.
 *
 * ```kotlin
 * val terminal = Xterm.create(Xterm.options { cols = 80; rows = 24; fontSize = 14.0 })
 * terminal.open(container)
 * terminal.write("\u001b[32mhello\u001b[0m")
 * ```
 *
 * Every type xterm uses is declared next to this file; addons live in [io.github.shijianjs.ktjs.xterm.addon]
 * and are separate npm packages (`@xterm/addon-fit` and friends).
 */
object Xterm {
    /** Builds a terminal; the same as calling `Terminal(options)`. */
    fun create(options: ITerminalConstructorOptions? = null): Terminal = Terminal(options)
}

/** Builds a terminal; the same as [Xterm.create]. */
fun createTerminal(options: ITerminalConstructorOptions? = null): Terminal = Xterm.create(options)

/** Builds the options object the constructor takes, field by field. */
fun options(build: ITerminalConstructorOptions.() -> Unit): ITerminalConstructorOptions = terminalOptions(build)
