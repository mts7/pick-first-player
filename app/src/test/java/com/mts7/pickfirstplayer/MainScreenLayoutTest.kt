package com.mts7.pickfirstplayer

import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.mts7.pickfirstplayer.ui.theme.PickFirstPlayerTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class MainScreenLayoutTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun mainScreen_selectionContent_doesNotOverlapTopBar() {
        rule.setContent {
            PickFirstPlayerTheme {
                MainScreen(
                    numberOfPlayers = 0,
                    player = 0,
                    updateNumber = {},
                    onExit = {},
                    onRefresh = {},
                )
            }
        }

        val topBarBottom = rule.onNodeWithContentDescription("Pick First Player")
            .fetchSemanticsNode().boundsInRoot.bottom

        val contentTop = rule.onNodeWithText("Tap the number of players.")
            .fetchSemanticsNode().boundsInRoot.top

        assertTrue(
            "Main content (top=$contentTop) overlaps the TopBar (bottom=$topBarBottom); " +
                "Scaffold's contentPadding must be applied to the content.",
            contentTop >= topBarBottom
        )
    }

    @Test
    fun mainScreen_resultContent_doesNotOverlapTopBar() {
        rule.setContent {
            PickFirstPlayerTheme {
                MainScreen(
                    numberOfPlayers = 5,
                    player = 2,
                    updateNumber = {},
                    onExit = {},
                    onRefresh = {},
                )
            }
        }

        val topBarBottom = rule.onNodeWithContentDescription("Pick First Player")
            .fetchSemanticsNode().boundsInRoot.bottom

        val contentTop = rule.onNodeWithText("Number of players: 5")
            .fetchSemanticsNode().boundsInRoot.top

        assertTrue(
            "Result content (top=$contentTop) overlaps the TopBar (bottom=$topBarBottom); " +
                "Scaffold's contentPadding must be applied to the content.",
            contentTop >= topBarBottom
        )
    }
}
