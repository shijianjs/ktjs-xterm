package io.github.shijianjs.ktjs.xterm.demo.page.overview

import io.github.shijianjs.ktjs.demo.page.DemoPage
import io.github.shijianjs.ktjs.demo.i18n.Binder
import io.github.shijianjs.ktjs.demo.i18n.Lang
import io.github.shijianjs.ktjs.demo.i18n.Langs
import io.github.shijianjs.ktjs.demo.i18n.Translations
import io.github.shijianjs.ktjs.demo.i18n.texts
import io.github.shijianjs.ktjs.demo.page.Blocks.paragraph
import io.github.shijianjs.ktjs.demo.page.Grid
import io.github.shijianjs.ktjs.demo.page.SectionBlock
import io.github.shijianjs.ktjs.dom.css.cssSheet
import io.github.shijianjs.ktjs.dom.css.dsl.child
import io.github.shijianjs.ktjs.dom.css.dsl.className
import io.github.shijianjs.ktjs.dom.css.dsl.invoke
import io.github.shijianjs.ktjs.dom.css.dsl.tag
import io.github.shijianjs.ktjs.dom.element.invoke
import io.github.shijianjs.ktjs.vaadin.VaadinTagName
import io.github.shijianjs.ktjs.vaadin.card.CardVariants
import io.github.shijianjs.ktjs.vaadin.card.createCard
import io.github.shijianjs.ktjs.vaadin.themablemixin.addTheme
import io.github.shijianjs.ktjs.xterm.demo.siteTexts
import web.cssom.ClassName
import web.cssom.Color
import web.cssom.CSSStyleSheet
import web.cssom.Display
import web.cssom.None
import web.cssom.number
import web.html.HTMLElement
import web.html.HtmlTagName

/**
 * Landing page: what this wrapper is, and the three pages a new reader opens first.
 *
 * A card is a `vaadin-card` inside a link: the whole surface is clickable, and middle-click, "copy link
 * address" and keyboard activation come from the anchor instead of from a click handler.
 */
internal class OverviewPage : DemoPage() {

    private val binder = Binder(overviewTexts)

    private val cards = mutableListOf<CardLink>()

    init {
        val what = SectionBlock.NAME { show() }
        binder.bind(what.heading) { it.whatHeading }
        what.body.append(binder.bind(paragraph()) { it.whatBody })

        val start = SectionBlock.NAME { show() }
        binder.bind(start.heading) { it.startHeading }
        binder.bind(start.hint) { it.startHint }
        start.body.append(Grid.NAME { show(240); add(*buildCards().toTypedArray()) })

        addSheet(CARD_SHEET)
        body.append(what.root, start.root)
    }

    private fun buildCards(): List<HTMLElement> = listOf("playground", "api", "conventions").map { id ->
        val summary = HtmlTagName.div()
        // The title comes from the frame's table after the first language is applied: one place names
        // a page, and the card is another view of that name.
        val card = createCard {
            addTheme(CardVariants.elevated)
            titleHeadingLevel = 4.0
            append(summary)
        }
        val link = HtmlTagName.a {
            href = "#/$id"
            classList.add(CARD_LINK)
            dataset["route"] = id
            append(card)
        }
        cards += CardLink(id, link, card, summary)
        link
    }

    override fun applyTranslation(lang: Lang) {
        val current = overviewTexts[lang]
        setTitle(current.title)
        setSummary(current.summary)
        binder.apply(lang)

        val site = siteTexts[lang]
        cards.forEach { entry ->
            entry.card.cardTitle = site.pageTitles[entry.id] ?: entry.id
            entry.summary.textContent = current.cardSummaries[entry.id].orEmpty()
        }
    }

    companion object : OverviewPageDefaultReference() {
        private val english: OverviewTexts = OverviewTexts(
            title = "Overview",
            summary = "A Kotlin/JS wrapper around @xterm/xterm: the terminal, its buffers and the addons it ships.",
            whatHeading = "What this wrapper is",
            whatBody = "One npm package, one module: `ktjs-xterm` maps what `@xterm/xterm` declares onto " +
                "Kotlin types the compiler checks. This demo is an ordinary consumer of that module — it builds " +
                "the terminal through `Xterm.create` and drives it with the same events a shell would.",
            startHeading = "Where to start",
            startHint = "Three pages: run the terminal, read the mapping, then the rules.",
            cardSummaries = mapOf(
                "playground" to "The one thing the wrapper exists for: a terminal created by Kotlin, " +
                    "opened into a sized block, typed into and fitted to its container.",
                "api" to "What the wrapper maps, and what stays upstream's business — the table a new module is " +
                    "cut from.",
                "conventions" to "The rules this module is written by: one module per package, no dynamic, sheets that " +
                    "package, no dynamic, sheets that belong to their component.",
            ),
        )
        private val chinese: OverviewTexts = OverviewTexts(
            title = "概览",
            summary = "围绕 @xterm/xterm 的 Kotlin/JS 封装：终端、缓冲区，以及上游附带的 addon。",
            whatHeading = "这个封装是什么",
            whatBody = "一个 npm 包对应一个模块：`ktjs-xterm` 把 `@xterm/xterm` 的声明映射成" +
                "编译器能检查的 Kotlin 类型。这个 demo 就是该模块的普通使用方——用 `Xterm.create` 建终端，" +
                "用真实 shell 会监听的那些事件驱动它。",
            startHeading = "从这里开始",
            startHint = "三页：先跑起来，再看映射，最后看规矩。",
            cardSummaries = mapOf(
                "playground" to "封装存在的理由：用 Kotlin 建一个终端，开进有高度的容器里，输入内容并适配容器。",
                "api" to "封装映射了什么、哪些仍是上游的事——新模块照着切的那张表。",
                "conventions" to "这个模块遵守的规矩：一个包一个模块、不用 dynamic、样式表属于它的组件。",
            ),
        )
        private val overviewTexts: Translations<OverviewTexts> = texts(
            Langs.EN to english,
            Langs.ZH_HANS to chinese,
        )
        /** The class the cards are found by in tests; it carries no style of its own. */
        private val CARD_LINK: ClassName = ClassName("card-link")

        /**
         * What the index needs from the wrapper — two declarations, each one caused by the anchor rather than by
         * the card: a link paints its text like a link and does not pass a row's height on to its children. The
         * card itself is left exactly as the component draws itself.
         */
        private val CARD_SHEET: CSSStyleSheet = cssSheet {
            className(CARD_LINK).invoke {
                display = Display.flex
                color = Color("inherit")
                textDecorationLine = None.none
            }

            // The grid stretches the anchor to the tallest card of its row; inside a flex anchor the card grows
            // to that height, so the surfaces of one row line up whatever their summaries are.
            className(CARD_LINK).child(tag(VaadinTagName.card)).invoke {
                flexGrow = number(1.0)
            }
        }
    }
}
