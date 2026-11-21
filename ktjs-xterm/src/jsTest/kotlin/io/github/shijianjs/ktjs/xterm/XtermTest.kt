package io.github.shijianjs.ktjs.xterm

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Checks the wrapper against the real `@xterm/xterm` package in Chromium.
 *
 * The cases stay on what is deterministic without a layout pass: construction, the options object, the
 * buffers and event subscriptions. Writing text and drawing are covered by the demo's end-to-end test,
 * where the terminal is opened into a real, sized element.
 */
class XtermTest {
    @Test
    fun constructionReadsTheInitOptions() {
        val terminal = Xterm.create(options { cols = 20; rows = 5 })

        assertEquals(20, terminal.cols)
        assertEquals(5, terminal.rows)

        terminal.dispose()
    }

    @Test
    fun optionsAreLiveAfterConstruction() {
        val terminal = Xterm.create()

        terminal.options.fontSize = 14.0
        terminal.options.cursorBlink = true

        assertEquals(14.0, terminal.options.fontSize)
        assertEquals(true, terminal.options.cursorBlink)

        terminal.dispose()
    }

    @Test
    fun themeBuildersCarryTheFields() {
        val theme = terminalTheme {
            foreground = "#e6edf3"
            background = "#0d1117"
            cursor = "#58a6ff"
        }

        assertEquals("#e6edf3", theme.foreground)
        assertEquals("#0d1117", theme.background)
        assertEquals("#58a6ff", theme.cursor)
    }

    @Test
    fun aFreshBufferStartsAtTheOrigin() {
        val terminal = Xterm.create(options { cols = 10; rows = 3 })

        val buffer = terminal.buffer.active
        assertEquals(0, buffer.cursorX)
        assertEquals(0, buffer.cursorY)
        assertEquals("normal", buffer.type)
        assertEquals(buffer, terminal.buffer.normal)
        assertEquals("", buffer.getNullCell().getChars())

        terminal.dispose()
    }

    @Test
    fun anUnopenedTerminalHasNoElementOrDimensions() {
        val terminal = Xterm.create(options { cols = 10; rows = 3 })

        assertNull(terminal.element)
        assertNull(terminal.dimensions)
        assertNull(terminal.getSelectionPosition())
        assertTrue(!terminal.hasSelection())

        terminal.dispose()
    }

    @Test
    fun eventSubscriptionsAreDisposables() {
        val terminal = Xterm.create()

        val bell = terminal.onBell.listen { }
        val data = terminal.onData.listen { text -> assertEquals(text, text) }
        val resize = terminal.onResize.listen { event -> assertTrue(event.rows >= 0) }
        assertTrue(!terminal.modes.applicationCursorKeysMode)

        bell.dispose()
        data.dispose()
        resize.dispose()
        terminal.dispose()
    }

    @Test
    fun addonsActivateWithoutATerminalElement() {
        val fit = io.github.shijianjs.ktjs.xterm.addon.FitAddon()
        val terminal = Xterm.create(options { cols = 10; rows = 3 })

        terminal.loadAddon(fit)
        assertNull(fit.proposeDimensions())

        terminal.dispose()
    }
}
