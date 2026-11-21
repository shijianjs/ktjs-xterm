package demo.e2e

import com.microsoft.playwright.Page
import com.microsoft.playwright.junit.UsePlaywright
import com.microsoft.playwright.options.WaitUntilState
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junitpioneer.jupiter.RetryingTest

/**
 * Smoke checks for the demo: the shared shell mounts, the drawer drives the router, the terminal is
 * created and rendered by xterm itself, the table and the list render their copy, and a language switch
 * rewrites the pages in place.
 *
 * The shell lives in a shadow tree, so the demo's own nodes are selected through `#demo` and the page
 * element the shell mounts — never through the document root.
 */
@UsePlaywright(DemoOptions::class)
class DemoPageTest {
    @RetryingTest(maxAttempts = 2)
    fun `overview renders the shell and the three cards`(page: Page) {
        openPage(page)

        assertTrue(page.title().contains("ktjs-xterm"), "title: ${page.title()}")
        assertTrue(page.locator("#demo img.layout__logo").isVisible(), "the shell serves the project logo")
        assertEquals("Overview", page.locator("#demo xterm-overview-page h1").first().textContent())

        val rows = page.locator("#demo vaadin-side-nav-item").count()
        assertTrue(rows >= 4, "drawer rows: $rows")

        val cards = page.locator("#demo xterm-overview-page a.card-link").count()
        assertEquals(3, cards, "one card per page behind the overview")
    }

    @RetryingTest(maxAttempts = 2)
    fun `a card opens the live terminal`(page: Page) {
        openPage(page)
        page.locator("#demo xterm-overview-page a.card-link[data-route='playground']").first().click()
        page.waitForSelector("#demo xterm-playground-page h1")

        assertEquals("Terminal", page.locator("#demo xterm-playground-page h1").first().textContent())
    }

    @RetryingTest(maxAttempts = 2)
    fun `xterm renders into the host and the sample reaches the buffer`(page: Page) {
        openPage(page)
        openRoute(page, "playground")

        // The wrapper hands the block to xterm; the screen layers are xterm's own DOM, so their presence
        // is the proof that open() ran.
        assertTrue(
            page.locator("#demo xterm-playground-page .xterm-host .xterm").count() > 0,
            "xterm mounted",
        )

        page.locator("#demo xterm-playground-page vaadin-button").first().click()
        // The screen layers live inside xterm's own DOM; `document.querySelector` cannot reach into the
        // page's shadow tree, so the wait goes through a Playwright locator, which does.
        awaitText(page, "#demo xterm-playground-page .xterm-host .xterm-rows", "compiled 42 sources")
    }

    @RetryingTest(maxAttempts = 2)
    fun `the mapping table and the conventions list render`(page: Page) {
        openPage(page)
        openRoute(page, "api")
        assertEquals("What the wrapper maps", page.locator("#demo xterm-api-page h1").first().textContent())
        assertEquals(8, page.locator("#demo xterm-api-page table tr").count(), "seven rows plus the header")

        openRoute(page, "conventions")
        assertEquals("Conventions", page.locator("#demo xterm-conventions-page h1").first().textContent())
        assertEquals(5, page.locator("#demo xterm-conventions-page ul li").count())
    }

    @RetryingTest(maxAttempts = 2)
    fun `the language switch rewrites the page in place`(page: Page) {
        openPage(page)
        openRoute(page, "playground")

        switchLanguage(page, "zh-Hans")
        assertEquals("终端", page.locator("#demo xterm-playground-page h1").first().textContent())
        assertEquals("写一段示例输出", page.locator("#demo xterm-playground-page vaadin-button").first().textContent())
    }

    @RetryingTest(maxAttempts = 2)
    fun `the page keeps a single scrollbar`(page: Page) {
        openPage(page)
        openRoute(page, "api")

        val horizontal = page.evaluate(
            "() => document.documentElement.scrollWidth - document.documentElement.clientWidth",
        ) as Number
        assertEquals(0, horizontal.toInt(), "no horizontal overflow")
    }

    @RetryingTest(maxAttempts = 2)
    fun `page logs no console errors or failed requests`(page: Page) {
        val consoleErrors = mutableListOf<String>()
        val failedRequests = mutableListOf<String>()
        page.onConsoleMessage { message ->
            if (message.type() == "error") consoleErrors += message.text()
        }
        page.onRequestFailed { request ->
            failedRequests += "${request.url()} ${request.failure()}"
        }

        openPage(page)
        openRoute(page, "playground")

        assertTrue(consoleErrors.isEmpty(), "console errors: $consoleErrors")
        assertTrue(failedRequests.isEmpty(), "failed requests: $failedRequests")
    }

    private fun openPage(page: Page) {
        page.navigate("/", Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE))
        page.waitForSelector("#demo xterm-overview-page h1")
    }

    /** Clicks the drawer item for [route]; the router is also what the menu drives. */    private fun openRoute(page: Page, route: String) {
        page.locator("#demo vaadin-side-nav-item[data-route='$route']").first().click()
        page.waitForSelector("#demo xterm-$route-page h1")
    }

    /**
     * Waits until the element at [selector] contains [expected].
     *
     * Playwright locators pierce open shadow roots; a `waitForFunction` calling `document.querySelector`
     * does not, which is why this polls through a locator instead of evaluating script.
     */
    private fun awaitText(page: Page, selector: String, expected: String) {
        val locator = page.locator(selector).first()
        val deadline = System.currentTimeMillis() + 15_000
        while (System.currentTimeMillis() < deadline) {
            if (locator.count() > 0 && locator.textContent().contains(expected)) return
            Thread.sleep(200)
        }
        throw AssertionError("'$expected' never reached $selector")
    }

    /** Opens the layout's language menu and picks [code]. */
    private fun switchLanguage(page: Page, code: String) {
        page.locator("#demo [data-menu='lang']").first().click()
        page.locator("#demo [data-action='lang:$code']").first().click()
        page.waitForFunction("(expected) => document.documentElement.lang === expected", code)
    }
}
