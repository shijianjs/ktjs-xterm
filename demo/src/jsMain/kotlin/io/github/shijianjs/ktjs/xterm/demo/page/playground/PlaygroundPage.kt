package io.github.shijianjs.ktjs.xterm.demo.page.playground

import io.github.shijianjs.ktjs.demo.i18n.Binder
import io.github.shijianjs.ktjs.demo.i18n.Lang
import io.github.shijianjs.ktjs.demo.i18n.Langs
import io.github.shijianjs.ktjs.demo.i18n.Translations
import io.github.shijianjs.ktjs.demo.i18n.texts
import io.github.shijianjs.ktjs.demo.page.Blocks.row
import io.github.shijianjs.ktjs.demo.page.DemoPage
import io.github.shijianjs.ktjs.demo.page.Readout
import io.github.shijianjs.ktjs.demo.page.SectionBlock
import io.github.shijianjs.ktjs.dom.css.cssSheet
import io.github.shijianjs.ktjs.dom.css.dsl.className
import io.github.shijianjs.ktjs.dom.css.dsl.invoke
import io.github.shijianjs.ktjs.dom.element.invoke
import io.github.shijianjs.ktjs.vaadin.button.createButton
import io.github.shijianjs.ktjs.vaadin.switch.SwitchEvents
import io.github.shijianjs.ktjs.vaadin.switch.createSwitch
import io.github.shijianjs.ktjs.xterm.Terminal
import io.github.shijianjs.ktjs.xterm.Xterm
import io.github.shijianjs.ktjs.xterm.addon.FitAddon
import io.github.shijianjs.ktjs.xterm.listen
import io.github.shijianjs.ktjs.xterm.options
import io.github.shijianjs.ktjs.xterm.terminalTheme
import web.cssom.ClassName
import web.cssom.Color
import web.cssom.FontFamily
import web.cssom.Padding
import web.cssom.pct
import web.cssom.px
import web.dom.clickEvent
import web.events.addHandler
import web.events.EventHandler
import web.events.EventType
import web.events.addEventListener
import web.html.HTMLDivElement
import web.html.HTMLElement
import web.html.HTMLLabelElement
import web.html.HtmlTagName

/**
 * A terminal you can type into.
 *
 * The host is a demo placeholder: xterm needs a sized block element, and the height below is what the
 * demo gives it, not something a component library would decide for the terminal.
 */
internal class PlaygroundPage : DemoPage() {

    private val binder = Binder(playgroundTexts)

    private val terminal: Terminal = Xterm.create(
        options {
            cols = 80
            rows = 14
            fontSize = 13.0
            fontFamily = "ui-monospace, SFMono-Regular, Menlo, monospace"
            cursorBlink = true
            convertEol = true
            theme = terminalTheme {
                background = "#0d1117"
                foreground = "#e6edf3"
                cursor = "#58a6ff"
                selectionBackground = "#264f78"
            }
        },
    )

    private val fit = FitAddon()

    private val host: HTMLDivElement = HtmlTagName.div { classList.add(HOST) }

    private val run = createButton { }
    private val fitButton = createButton { }
    private val clear = createButton { }
    private val blink = createSwitch { checked = true }

    /** A switch's own label is a property; this page's words are text nodes the binder can rewrite. */
    private val blinkLabel = HtmlTagName.label { }

    private val size = Readout.NAME { show() }
    private val cursor = Readout.NAME { show() }
    private val selection = Readout.NAME { show() }

    private val sizeLabel = HtmlTagName.label { }
    private val cursorLabel = HtmlTagName.label { }
    private val selectionLabel = HtmlTagName.label { }

    private var current: PlaygroundTexts = english

    init {
        terminal.loadAddon(fit)
        terminal.open(host)
        // Echo what the user types, so this is a terminal rather than a picture of one.
        terminal.onData.listen { data -> terminal.write(data) }
        terminal.onResize.listen { event -> report(event.cols, event.rows) }
        terminal.onSelectionChange.listen { report(terminal.cols, terminal.rows) }

        run.clickEvent.addHandler { writeSample() }
        fitButton.clickEvent.addHandler { fit.fit(); report(terminal.cols, terminal.rows) }
        clear.clickEvent.addHandler { terminal.clear() }
        blink.addEventListener(SwitchEvents.checkedChanged, EventHandler { terminal.options.cursorBlink = blink.checked })

        val section = SectionBlock.NAME { show() }
        binder.bind(section.heading) { it.terminalHeading }
        binder.bind(section.hint) { it.terminalHint }
        binder.bind(run) { it.runLabel }
        binder.bind(fitButton) { it.fitLabel }
        binder.bind(clear) { it.clearLabel }
        binder.bind(blinkLabel) { it.blinkLabel }
        binder.bind(sizeLabel) { it.sizeLabel }
        binder.bind(cursorLabel) { it.cursorLabel }
        binder.bind(selectionLabel) { it.selectionLabel }

        section.body.append(
            host,
            row(run, fitButton, clear, blink, blinkLabel),
            row(labelled(sizeLabel, size.root), labelled(cursorLabel, cursor.root), labelled(selectionLabel, selection.root)),
        )

        addSheet(HOST_SHEET)
        body.append(section.root)
        report(terminal.cols, terminal.rows)
    }

    private fun labelled(label: HTMLLabelElement, readout: HTMLElement): HTMLDivElement {
        val wrapper = HtmlTagName.div()
        wrapper.append(label, readout)
        return wrapper
    }

    private fun report(cols: Int, rows: Int) {
        size.set("$cols \u00d7 $rows")
        val buffer = terminal.buffer.active
        cursor.set("${buffer.cursorX}, ${buffer.cursorY}")
        val text = terminal.getSelection()
        selection.set(if (text.isEmpty()) "\u2014" else text)
    }

    private fun writeSample() {
        terminal.write(current.sample, { report(terminal.cols, terminal.rows) })
    }

    override fun applyTranslation(lang: Lang) {
        current = playgroundTexts[lang]
        setTitle(current.title)
        setSummary(current.summary)
        binder.apply(lang)
        report(terminal.cols, terminal.rows)
    }

    companion object : PlaygroundPageDefaultReference() {
        private val english: PlaygroundTexts = PlaygroundTexts(
            title = "Terminal",
            summary = "A live terminal: type into it, fit it, clear it — all through the wrapper.",
            terminalHeading = "Live terminal",
            terminalHint = "Typing is echoed through `onData`, the same event a real shell listens to.",
            runLabel = "Write sample output",
            fitLabel = "Fit to container",
            clearLabel = "Clear viewport",
            blinkLabel = "Cursor blink",
            sizeLabel = "cols × rows",
            cursorLabel = "cursor",
            selectionLabel = "selection",
            sample = "\u001b[1;34m$\u001b[0m npm run build\r\n" +
                "\u001b[32m\u2714\u001b[0m compiled 42 sources in \u001b[1m2.4s\u001b[0m\r\n" +
                "\u001b[33m\u26a0\u001b[0m 3 warnings, 0 errors\r\n" +
                "\u001b[1;34m$\u001b[0m ",
        )
        private val chinese: PlaygroundTexts = PlaygroundTexts(
            title = "终端",
            summary = "一个能用的终端：输入、适配、清屏，全部经过封装层。",
            terminalHeading = "实时终端",
            terminalHint = "输入通过 `onData` 回显——真实 shell 监听的就是同一个事件。",
            runLabel = "写一段示例输出",
            fitLabel = "适配容器",
            clearLabel = "清屏",
            blinkLabel = "光标闪烁",
            sizeLabel = "列数 × 行数",
            cursorLabel = "光标",
            selectionLabel = "选中内容",
            sample = "\u001b[1;34m$\u001b[0m npm run build\r\n" +
                "\u001b[32m\u2714\u001b[0m 已编译 42 个源文件，用时 \u001b[1m2.4s\u001b[0m\r\n" +
                "\u001b[33m\u26a0\u001b[0m 3 个警告，0 个错误\r\n" +
                "\u001b[1;34m$\u001b[0m ",
        )
        private val playgroundTexts: Translations<PlaygroundTexts> = texts(
            Langs.EN to english,
            Langs.ZH_HANS to chinese,
        )

        private val HOST: ClassName = ClassName("xterm-host")

        /** Demo placeholder: the block xterm draws into, sized by the demo rather than by a component. */
        private val HOST_SHEET = cssSheet {
            className(HOST).invoke {
                width = 100.pct
                height = 280.px
                padding = Padding(8.px, 8.px)
                background = Color("#0d1117")
                fontFamily = FontFamily.monospace
            }
        }
    }
}
