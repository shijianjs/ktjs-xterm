package io.github.shijianjs.ktjs.xterm.demo.page.conventions

import io.github.shijianjs.ktjs.demo.page.DemoPage
import web.dom.TagName
import io.github.shijianjs.ktjs.demo.i18n.Binder
import io.github.shijianjs.ktjs.demo.i18n.Lang
import io.github.shijianjs.ktjs.demo.i18n.Langs
import io.github.shijianjs.ktjs.demo.i18n.Translations
import io.github.shijianjs.ktjs.demo.i18n.texts
import io.github.shijianjs.ktjs.demo.page.Blocks.bulletList
import io.github.shijianjs.ktjs.demo.page.SectionBlock
import io.github.shijianjs.ktjs.dom.element.invoke
import web.html.HTMLElement
import web.html.HtmlTagName

/**
 * The rules the library and the demo are both written by, as a list.
 *
 * A list of whole lines is the one case where replacing the collection is right: each line comes from
 * one language, the nodes are this page's own, and nothing above them is touched.
 */
internal class ConventionsPage : DemoPage() {

    private val binder = Binder(conventionsTexts)

    private val list: HTMLElement = bulletList(emptyList())

    init {
        val section = SectionBlock.NAME { show() }
        binder.bind(section.heading) { it.heading }
        binder.bind(section.hint) { it.hint }
        section.body.append(list)

        body.append(section.root)
    }

    override fun applyTranslation(lang: Lang) {
        val current = conventionsTexts[lang]
        setTitle(current.title)
        setSummary(current.summary)
        binder.apply(lang)

        list.replaceChildren(*current.lines.map { HtmlTagName.li { textContent = it } }.toTypedArray())
    }

    companion object : ConventionsPageDefaultReference() {
        private val english: ConventionsTexts = ConventionsTexts(
            title = "Conventions",
            summary = "The rules the wrapper is written by: no dynamic, one place where a runtime-chosen return " +
                "type is narrowed, and sheets that belong to their page.",
            heading = "Rules this wrapper follows",
            hint = "These are what a review of this module looks for.",
            lines = listOf(
                "Declarations live in jsMain only: external classes, interfaces and typealiases, no dynamic.",
                "@file:JsModule files hold nothing but what the npm package exports — no global objects.",
                "A callable event object (IEvent<T>) is modelled as a function type, so listen() is a real call.",
                "Optional constructor arguments use definedExternally; an explicit null is not the same thing.",
                "The provider's constructor takes one object, so the wrapper declares the intersection as one interface.",
            ),
        )
        private val chinese: ConventionsTexts = ConventionsTexts(
            title = "约定",
            summary = "封装层自己的规矩：不用 dynamic，运行时决定返回类型的地方只收口一次，样式表归属它的页面。",
            heading = "这个封装遵守的规矩",
            hint = "评审这个模块时就照这份清单看。",
            lines = listOf(
                "声明只放 jsMain：external class / interface / typealias，不写 dynamic。",
                "@file:JsModule 的文件里只放 npm 包导出的东西，全局对象另起文件。",
                "可调用的事件对象（IEvent<T>）建模成函数类型，listen() 因此是一次真实调用。",
                "可选构造参数用 definedExternally；显式传 null 和省略不是一回事。",
                "上游构造器只吃一个对象，封装层就把那个交叉类型声明成一个接口。",
            ),
        )
        private val conventionsTexts: Translations<ConventionsTexts> = texts(
            Langs.EN to english,
            Langs.ZH_HANS to chinese,
        )
    }
}
