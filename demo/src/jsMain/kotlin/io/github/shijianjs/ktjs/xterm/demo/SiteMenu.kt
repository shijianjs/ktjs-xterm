package io.github.shijianjs.ktjs.xterm.demo

import io.github.shijianjs.ktjs.demo.nav.DemoMenu
import io.github.shijianjs.ktjs.demo.nav.Entry
import io.github.shijianjs.ktjs.demo.nav.Group
import io.github.shijianjs.ktjs.xterm.demo.page.api.ApiPage
import io.github.shijianjs.ktjs.xterm.demo.page.playground.PlaygroundPage
import io.github.shijianjs.ktjs.xterm.demo.page.conventions.ConventionsPage
import io.github.shijianjs.ktjs.xterm.demo.page.overview.OverviewPage
import io.github.shijianjs.ktjs.dom.element.invoke

/** The Kotlin version this module builds with; the navbar shows it next to the title. */
internal const val SITE_VERSION: String = "Kotlin/JS 2.4.21"

/** The documentation site of this wrapper. */
internal const val DOCS_URL: String = "https://shijianjs.github.io/ktjs-xterm/"

/** Dokka reference assembled by pagesSite next to the demo. */
internal const val API_URL: String = "https://shijianjs.github.io/ktjs-xterm/api/"

/** Source repository, linked from the navbar's link menu. */
internal const val SOURCE_URL: String = "https://github.com/shijianjs/ktjs-xterm"

/**
 * Groups are the drawer's sections; [icon] is an Iconify name drawn before the group title.
 *
 * A group with a single page is lifted to the top level by the layout, so `START` keeps its mark without
 * becoming a row that only exists to be opened again.
 */
internal val START: Group = Group("start", "lucide:sparkles")

internal val CONSOLE: Group = Group("console", "lucide:terminal")

internal val REFERENCE: Group = Group("reference", "lucide:book-open")

/**
 * The site: one entry per page, in navigation order.
 *
 * Adding a page is one entry here plus its titles in [siteTexts] — the drawer, the routes, the
 * breadcrumbs and the document title all derive from this list.
 */
internal fun siteMenu(): DemoMenu = DemoMenu(
    listOf(
        Entry("overview", START) { OverviewPage.NAME() },
        Entry("playground", CONSOLE) { PlaygroundPage.NAME() },
        Entry("api", REFERENCE) { ApiPage.NAME() },
        Entry("conventions", REFERENCE) { ConventionsPage.NAME() },
    ),
)
