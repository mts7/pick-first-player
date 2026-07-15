package com.mts7.pickfirstplayer

import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// Launches the real MainActivity, unlike MainActivityComposablesTest which mounts BottomBar
// in isolation with mocked callbacks. These verify the actual wiring through MainActivity's
// updateNumber/exitApplication, not just that BottomBar's own callback parameters fire.
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class MainActivityIntegrationTest {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun resetButton_returnsToSelectionScreen() {
        rule.onNodeWithText("5").performClick()
        rule.onNodeWithText("Number of players: 5").assertExists()

        rule.onNodeWithText("Reset").performClick()

        rule.onNodeWithText("Tap the number of players.").assertExists()
    }

    @Test
    fun exitButton_finishesTheActivity() {
        rule.onNodeWithText("Exit").performClick()

        assertTrue(rule.activity.isFinishing)
    }

    @Test
    fun numberOfPlayers_survivesActivityRecreation() {
        rule.onNodeWithText("5").performClick()
        rule.onNodeWithText("Number of players: 5").assertExists()

        rule.activityRule.scenario.recreate()

        rule.onNodeWithText("Number of players: 5").assertExists()
    }
}
