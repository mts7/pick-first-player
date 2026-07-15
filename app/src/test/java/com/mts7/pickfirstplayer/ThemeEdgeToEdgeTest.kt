package com.mts7.pickfirstplayer

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class ThemeEdgeToEdgeTest {
    private val themeSource =
        File("src/main/java/com/mts7/pickfirstplayer/ui/theme/Theme.kt").readText()
    private val mainActivitySource =
        File("src/main/java/com/mts7/pickfirstplayer/MainActivity.kt").readText()

    @Test
    fun theme_doesNotSetDeprecatedStatusBarColor() {
        assertFalse(
            "Theme.kt should not set window.statusBarColor directly: it's deprecated and is " +
                "ignored once the app draws edge-to-edge. Use enableEdgeToEdge() plus " +
                "WindowInsets-aware padding on the affected composables instead.",
            themeSource.contains("statusBarColor")
        )
    }

    @Test
    fun mainActivity_enablesEdgeToEdge() {
        assertTrue(
            "Expected MainActivity to call enableEdgeToEdge() so system bar handling is " +
                "explicit and consistent across API levels.",
            mainActivitySource.contains("enableEdgeToEdge(")
        )
    }
}
