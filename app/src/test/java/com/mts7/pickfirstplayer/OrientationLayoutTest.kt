package com.mts7.pickfirstplayer

import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.mts7.pickfirstplayer.ui.theme.PickFirstPlayerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// MainLayout and ResultScreen both branch on isPortrait, but until now that branch was only
// ever reachable through unused @Preview functions -- no test exercised isPortrait = false.
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class OrientationLayoutTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun mainLayout_portrait_rendersHeadingAndAllButtons() {
        rule.setContent {
            PickFirstPlayerTheme {
                MainLayout(onNumberClick = {}, isPortrait = true)
            }
        }

        rule.onNodeWithText("Tap the number of players.").assertExists()
        for (number in 2..7) {
            rule.onNodeWithText(number.toString()).assertExists()
        }
    }

    @Test
    fun mainLayout_landscape_rendersHeadingAndAllButtons() {
        rule.setContent {
            PickFirstPlayerTheme {
                MainLayout(onNumberClick = {}, isPortrait = false)
            }
        }

        rule.onNodeWithText("Tap the number of players.").assertExists()
        for (number in 2..7) {
            rule.onNodeWithText(number.toString()).assertExists()
        }
    }

    @Test
    fun resultScreen_portrait_rendersChosenValueAndDirection() {
        rule.setContent {
            PickFirstPlayerTheme {
                ResultScreen(
                    maxValue = 6,
                    direction = Direction.LEFT,
                    places = 3,
                    refreshSelection = {},
                    isPortrait = true,
                )
            }
        }

        rule.onNodeWithText("Number of players: 6").assertExists()
        rule.onNodeWithText("The player 3 to your left goes first.").assertExists()
    }

    @Test
    fun resultScreen_landscape_rendersChosenValueAndDirection() {
        rule.setContent {
            PickFirstPlayerTheme {
                ResultScreen(
                    maxValue = 6,
                    direction = Direction.LEFT,
                    places = 3,
                    refreshSelection = {},
                    isPortrait = false,
                )
            }
        }

        rule.onNodeWithText("Number of players: 6").assertExists()
        rule.onNodeWithText("The player 3 to your left goes first.").assertExists()
    }
}
