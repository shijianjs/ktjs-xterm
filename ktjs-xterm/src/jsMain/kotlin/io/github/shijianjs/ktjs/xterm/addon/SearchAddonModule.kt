@file:JsModule("@xterm/addon-search")

package io.github.shijianjs.ktjs.xterm.addon

import io.github.shijianjs.ktjs.xterm.ITerminalAddon
import io.github.shijianjs.ktjs.xterm.Terminal
import io.github.shijianjs.ktjs.xterm.TerminalEvent
import io.github.shijianjs.ktjs.xterm.VoidEvent
import kotlin.js.definedExternally

/** Finds text in the buffer and highlights the matches. */
external class SearchAddon : ITerminalAddon {
    constructor(options: ISearchAddonOptions? = definedExternally)

    override fun activate(terminal: Terminal)

    override fun dispose()

    fun findNext(term: String, searchOptions: ISearchOptions? = definedExternally): Boolean

    fun findPrevious(term: String, searchOptions: ISearchOptions? = definedExternally): Boolean

    fun clearDecorations()

    fun clearActiveDecoration()

    val onAfterSearch: VoidEvent
    val onBeforeSearch: VoidEvent
    val onDidChangeResults: TerminalEvent<ISearchResultChangeEvent>
}

/** How a search matches and how the matches are painted. */
external interface ISearchOptions {
    var regex: Boolean?
    var wholeWord: Boolean?
    var caseSensitive: Boolean?
    var incremental: Boolean?
    var decorations: ISearchDecorationOptions?
}

external interface ISearchDecorationOptions {
    var matchBackground: String?
    var matchBorder: String?
    var matchOverviewRuler: String?
    var activeMatchBackground: String?
    var activeMatchBorder: String?
    var activeMatchColorOverviewRuler: String?
}

external interface ISearchResultChangeEvent {
    var resultIndex: Int
    var resultCount: Int
}

external interface ISearchAddonOptions {
    var highlightLimit: Int?
}
