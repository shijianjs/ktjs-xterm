package io.github.shijianjs.ktjs.xterm.demo.page.api

import io.github.shijianjs.ktjs.demo.page.DemoPage
import io.github.shijianjs.ktjs.demo.i18n.Binder
import io.github.shijianjs.ktjs.demo.i18n.Lang
import io.github.shijianjs.ktjs.demo.i18n.Langs
import io.github.shijianjs.ktjs.demo.i18n.Translations
import io.github.shijianjs.ktjs.demo.i18n.texts
import io.github.shijianjs.ktjs.demo.page.Blocks.exampleBox
import io.github.shijianjs.ktjs.demo.page.SectionBlock
import io.github.shijianjs.ktjs.dom.css.cssSheet
import io.github.shijianjs.ktjs.dom.css.dsl.className
import io.github.shijianjs.ktjs.dom.css.dsl.invoke
import io.github.shijianjs.ktjs.dom.element.invoke
import web.cssom.BorderCollapse
import web.cssom.CSSStyleSheet
import web.cssom.ClassName
import web.cssom.Padding
import web.cssom.TextAlign
import web.cssom.integer
import web.cssom.pct
import web.cssom.px
import web.html.HTMLTableRowElement
import web.html.HTMLElement
import web.html.HtmlTagName

/**
 * The mapping table: what the module declares, and what it deliberately leaves to upstream.
 *
 * The right column is code, identical in both languages, so a switch only rewrites the left column.
 */
internal class ApiPage : DemoPage() {

    private val binder = Binder(apiTexts)

    private val headRow: HTMLTableRowElement = HtmlTagName.tr()

    private val bodyRows = HtmlTagName.tbody()

    private val rows: List<ApiRow>

    init {
        rows = english.rows.map {
            val labelCell = HtmlTagName.td { classList.add(API_CELL) }
            val valueCell = HtmlTagName.td { classList.add(API_CELL) }
            bodyRows.append(HtmlTagName.tr { append(labelCell, valueCell) })
            ApiRow(labelCell, valueCell)
        }

        val table = HtmlTagName.table {
            classList.add(API_TABLE)
            append(HtmlTagName.thead { append(headRow) }, bodyRows)
        }

        val section = SectionBlock.NAME { show() }
        binder.bind(section.heading) { it.heading }
        binder.bind(section.hint) { it.hint }
        section.body.append(exampleBox(null, table))

        addSheet(TABLE_SHEET)
        body.append(section.root)
    }

    override fun applyTranslation(lang: Lang) {
        val current = apiTexts[lang]
        setTitle(current.title)
        setSummary(current.summary)
        binder.apply(lang)

        headRow.replaceChildren(
            HtmlTagName.th {
                classList.add(API_CELL)
                classList.add(API_HEAD_CELL)
                textContent = current.upstreamHeading
            },
            HtmlTagName.th {
                classList.add(API_CELL)
                classList.add(API_HEAD_CELL)
                textContent = current.kotlinHeading
            },
        )
        current.rows.zip(rows).forEach { (row, cells) ->
            cells.labelCell.textContent = row.first
            cells.valueCell.textContent = row.second
        }
    }

    companion object : ApiPageDefaultReference() {
        private val english: ApiTexts = ApiTexts(
            title = "What the wrapper maps",
            summary = "Only what upstream declares as an API is mapped; the terminal itself stays upstream.",
            heading = "Property by property",
            hint = "Read the upstream declaration first, then decide the Kotlin type — the table is the whole difference.",
            upstreamHeading = "upstream",
            kotlinHeading = "in Kotlin",
            rows = listOf(
                "npm package" to "@xterm/xterm 6.0.0",
                "module shape" to "class Terminal, the only runtime export",
                "constructor options" to "ITerminalConstructorOptions (options + cols/rows)",
                "events" to "onData / onKey / onResize ... as function types",
                "buffers" to "IBufferNamespace, IBuffer, IBufferLine, IBufferCell",
                "addons" to "5 of the 15 published addons, each its own npm package",
                "sizes" to "cells for rows/cols, CSS pixels for fontSize and paddings",
            ),
        )
        private val chinese: ApiTexts = ApiTexts(
            title = "封装层映射了什么",
            summary = "只映射上游声明为 API 的东西；终端本身的渲染仍归上游。",
            heading = "逐条对照",
            hint = "先读上游声明，再决定 Kotlin 类型——差别全在这张表里。",
            upstreamHeading = "上游",
            kotlinHeading = "Kotlin 侧",
            rows = listOf(
                "npm 包" to "@xterm/xterm 6.0.0",
                "模块形状" to "class Terminal，唯一的运行时导出",
                "构造选项" to "ITerminalConstructorOptions（选项 + cols/rows）",
                "事件" to "onData / onKey / onResize …… 映射成函数类型",
                "缓冲区" to "IBufferNamespace、IBuffer、IBufferLine、IBufferCell",
                "addon" to "上游 15 个 addon 中的 5 个，各自是一个 npm 包",
                "尺寸" to "行列用 cell 数，字号与内边距用 CSS 像素",
            ),
        )
        private val apiTexts: Translations<ApiTexts> = texts(
            Langs.EN to english,
            Langs.ZH_HANS to chinese,
        )
        /** The table's own class names: only this page selects and carries them. */
        private val API_TABLE: ClassName = ClassName("api-table")
        private val API_CELL: ClassName = ClassName("api-cell")
        private val API_HEAD_CELL: ClassName = ClassName("api-cell-head")

        /**
         * A static definition table has no Vaadin component counterpart — `vaadin-grid` is the element for data
         * a user navigates, and this is six fixed rows — so it stays a real `<table>`.
         *
         * The rules are the table's own: collapse the borders, one padding per cell, and the header weight. Colours
         * come from Lumo's tokens, so the table follows the theme like everything else on the page.
         */
        private val TABLE_SHEET: CSSStyleSheet = cssSheet {
            className(API_TABLE).invoke {
                width = 100.pct
                borderCollapse = BorderCollapse.collapse
            }

            className(API_CELL).invoke {
                padding = Padding(6.px, 8.px)
            }

            className(API_HEAD_CELL).invoke {
                width = 32.pct
                fontWeight = integer(600)
                textAlign = TextAlign.left
            }
        }
    }
}
