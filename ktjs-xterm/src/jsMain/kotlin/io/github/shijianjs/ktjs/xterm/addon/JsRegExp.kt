package io.github.shijianjs.ktjs.xterm.addon

import kotlin.js.definedExternally
import kotlin.js.js

/**
 * A JavaScript `RegExp`.
 *
 * It lives outside the `@JsModule("@xterm/addon-web-links")` file on purpose: inside that file every
 * declaration is read as an import of the npm package, while `RegExp` is a global constructor.
 */
external class JsRegExp(pattern: String, flags: String = definedExternally)

/** Builds a `RegExp`, for [IWebLinksProviderOptions.urlRegex]. */
fun jsRegExp(pattern: String, flags: String = ""): JsRegExp = js("new RegExp(pattern, flags)")
