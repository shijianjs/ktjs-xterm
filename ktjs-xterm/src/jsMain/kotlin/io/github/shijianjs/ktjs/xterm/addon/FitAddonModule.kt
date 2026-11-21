@file:JsModule("@xterm/addon-fit")

package io.github.shijianjs.ktjs.xterm.addon

import io.github.shijianjs.ktjs.xterm.ITerminalAddon

/**
 * Resizes the terminal to the size of its container.
 *
 * Call [fit] whenever the container changes size; [proposeDimensions] is the calculation on its own,
 * `null` until the terminal has been opened.
 */
external class FitAddon : ITerminalAddon {
    constructor()

    override fun activate(terminal: io.github.shijianjs.ktjs.xterm.Terminal)

    override fun dispose()

    fun fit()

    fun proposeDimensions(): IFitDimensions?
}

/** The cell size [FitAddon.proposeDimensions] reports. */
external interface IFitDimensions {
    var rows: Int
    var cols: Int
}
