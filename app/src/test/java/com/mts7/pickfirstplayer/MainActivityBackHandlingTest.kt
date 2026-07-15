package com.mts7.pickfirstplayer

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class MainActivityBackHandlingTest {
    private val mainActivitySource =
        File("src/main/java/com/mts7/pickfirstplayer/MainActivity.kt").readText()

    @Test
    fun backPressHandling_isNotRegisteredDirectlyInsideComposableBody() {
        assertFalse(
            "onBackPressedDispatcher.addCallback must not be called directly inside setContent's " +
                "composable body: it re-registers a new callback on every recomposition, leaking " +
                "callbacks that are never removed. Use the BackHandler composable instead.",
            mainActivitySource.contains("onBackPressedDispatcher.addCallback")
        )
    }

    @Test
    fun backPressHandling_usesBackHandlerComposable() {
        assertTrue(
            "Expected MainActivity to handle back presses with the BackHandler composable, " +
                "which scopes callback registration to composition lifecycle automatically.",
            mainActivitySource.contains("BackHandler")
        )
    }

    @Test
    fun backPressHandling_isDisabledOnTheHomeScreen() {
        assertTrue(
            "Expected BackHandler's enabled param to be conditioned on numberOfPlayers so that " +
                "on the home screen (numberOfPlayers == 0) the callback is disabled and back is " +
                "handled by Android, not the app.",
            mainActivitySource.contains("enabled = numberOfPlayers")
        )
    }

    @Test
    fun exitApplication_doesNotForceKillTheProcess() {
        assertFalse(
            "exitApplication should not call exitProcess/System.exit -- that's a JVM/console " +
                "idiom that skips Android's normal teardown. finish() alone is sufficient.",
            mainActivitySource.contains("exitProcess")
        )
    }
}
