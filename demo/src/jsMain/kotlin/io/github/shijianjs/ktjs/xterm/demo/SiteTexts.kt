package io.github.shijianjs.ktjs.xterm.demo

import io.github.shijianjs.ktjs.demo.i18n.Langs
import io.github.shijianjs.ktjs.demo.i18n.SiteTexts
import io.github.shijianjs.ktjs.demo.i18n.Translations
import io.github.shijianjs.ktjs.demo.i18n.texts

/**
 * The strings the frame shows — navbar buttons, menu entries and the names of groups and pages.
 *
 * One object per language, declared once in [siteTexts]. Body copy of a page is not here: every page
 * owns its own text class the same way, so a page can be edited without touching the frame.
 */
private val englishSite: SiteTexts = SiteTexts(
    siteTitle = "ktjs-xterm",
    langLabel = "English",
    languageLabel = "Language",
    themeLabel = "Theme",
    docsLabel = "Docs",
    siteDocsLabel = "Documentation",
    apiLabel = "API reference",
    sourceLabel = "GitHub",
    lightLabel = "Light",
    darkLabel = "Dark",
    systemLabel = "Follow browser",
    groupTitles = mapOf(
        "start" to "Getting started",
        "console" to "The terminal",
        "reference" to "Reference",
    ),
    pageTitles = mapOf(
        "overview" to "Overview",
        "playground" to "Live terminal",
        "api" to "What the wrapper maps",
        "conventions" to "Conventions",
    ),
)

private val chineseSite: SiteTexts = SiteTexts(
    siteTitle = "ktjs-xterm",
    langLabel = "中文",
    languageLabel = "语言",
    themeLabel = "外观",
    docsLabel = "文档",
    siteDocsLabel = "使用文档",
    apiLabel = "API 文档",
    sourceLabel = "GitHub 仓库",
    lightLabel = "亮色",
    darkLabel = "暗色",
    systemLabel = "跟随浏览器",
    groupTitles = mapOf(
        "start" to "开始",
        "console" to "终端",
        "reference" to "参考",
    ),
    pageTitles = mapOf(
        "overview" to "概览",
        "playground" to "实时终端",
        "api" to "封装层映射了什么",
        "conventions" to "约定",
    ),
)

/** The layout's strings, one entry per registered language. */
internal val siteTexts: Translations<SiteTexts> = texts(
    Langs.EN to englishSite,
    Langs.ZH_HANS to chineseSite,
)
