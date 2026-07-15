package com.mts7.pickfirstplayer

import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MainActivityLayoutInstrumentedTest {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun topBar_doesNotOverlapMainContent() {
        val topBarBottom = rule.onNodeWithContentDescription("Pick First Player")
            .fetchSemanticsNode().boundsInRoot.bottom

        val contentTop = rule.onNodeWithText("Tap the number of players.")
            .fetchSemanticsNode().boundsInRoot.top

        assertTrue(
            "Main content (top=$contentTop) overlaps the real TopBar (bottom=$topBarBottom). " +
                "Once edge-to-edge is enabled, Scaffold's contentPadding must be applied and " +
                "the bars must reserve space for the status/navigation bar insets.",
            contentTop >= topBarBottom
        )
    }

    @Test
    fun topBar_doesNotOverlapResultContent() {
        rule.onNodeWithText("5").performClick()

        val topBarBottom = rule.onNodeWithContentDescription("Pick First Player")
            .fetchSemanticsNode().boundsInRoot.bottom

        val contentTop = rule.onNodeWithText("Number of players: 5")
            .fetchSemanticsNode().boundsInRoot.top

        assertTrue(
            "Result content (top=$contentTop) overlaps the real TopBar (bottom=$topBarBottom).",
            contentTop >= topBarBottom
        )
    }

    @Test
    fun topBar_isNotObscuredByTheSystemStatusBar() {
        val statusBarInsetPx = ViewCompat.getRootWindowInsets(rule.activity.window.decorView)
            ?.getInsets(WindowInsetsCompat.Type.statusBars())
            ?.top ?: 0

        val topBarTop = rule.onNodeWithContentDescription("Pick First Player")
            .fetchSemanticsNode().boundsInRoot.top

        assertTrue(
            "TopBar logo (top=$topBarTop) is drawn under the system status bar " +
                "(inset height=${statusBarInsetPx}px). Apply Modifier.statusBarsPadding() to TopBar.",
            topBarTop >= statusBarInsetPx
        )
    }
}
