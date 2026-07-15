package com.mts7.pickfirstplayer

import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.NoActivityResumedException
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test

// Real emulator, not Robolectric: these exercise the actual OnBackPressedDispatcher/predictive
// back path. We already hit a bug once (Gemini's Key.Back-based test) where a simulated back
// dispatch silently never reached the real callback -- only real OS dispatch caught it.
class MainActivityBackPressInstrumentedTest {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun backPress_afterSelectingPlayerCount_returnsToSelectionScreen() {
        rule.onNodeWithText("5").performClick()
        rule.onNodeWithText("Number of players: 5").assertExists()

        pressBack()

        rule.onNodeWithText("Tap the number of players.").assertExists()
    }

    @Test
    fun backPress_onHomeScreen_finishesTheActivity() {
        rule.onNodeWithText("Tap the number of players.").assertExists()

        // Espresso throws this specifically when a back press leaves no activity resumed --
        // i.e. it's how Espresso reports "yes, the app was exited," not a test failure.
        try {
            pressBack()
            fail("Expected back on the home screen to exit the app (no activity resumed).")
        } catch (expected: NoActivityResumedException) {
            // Confirmed: back was handled by Android, not intercepted by the app.
        }
    }
}
