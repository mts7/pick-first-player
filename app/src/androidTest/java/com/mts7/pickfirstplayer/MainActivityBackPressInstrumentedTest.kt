package com.mts7.pickfirstplayer

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.pressKey
import org.junit.Rule
import org.junit.Test

class MainActivityBackPressInstrumentedTest {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun backPress_afterSelectingPlayerCount_returnsToSelectionScreen() {
        rule.onNodeWithText("5").performClick()
        rule.onNodeWithText("Number of players: 5").assertExists()

        rule.onRoot().performKeyInput {
            pressKey(Key.Back)
        }

        rule.onNodeWithText("Tap the number of players.").assertExists()
    }
}
