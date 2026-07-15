package com.mts7.pickfirstplayer

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.v2.createComposeRule
import com.mts7.pickfirstplayer.ui.theme.Blue20
import com.mts7.pickfirstplayer.ui.theme.Blue60
import com.mts7.pickfirstplayer.ui.theme.Blue80
import com.mts7.pickfirstplayer.ui.theme.PickFirstPlayerTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// Regression coverage for the theming bug found earlier in this project: dynamicColor
// defaulting to true silently overrode this app's Blue palette with wallpaper-derived
// Material You colors on every Android 12+ device. These tests assert the resolved
// MaterialTheme.colorScheme directly, so a future accidental re-enable (or a copy-paste
// color swap) fails a fast JVM test instead of only being caught by eyeballing a screenshot.
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ThemeColorSchemeTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun lightTheme_usesTheAppsBluePalette() {
        val colorScheme = resolveColorScheme(darkTheme = false)

        assertEquals(Blue20, colorScheme.primary)
        assertEquals(Color.Black, colorScheme.secondary)
        assertEquals(Blue60, colorScheme.tertiary)
        assertEquals(Blue80, colorScheme.primaryContainer)
        assertEquals(Color.White, colorScheme.onPrimary)
    }

    @Test
    fun darkTheme_usesTheAppsBluePalette() {
        val colorScheme = resolveColorScheme(darkTheme = true)

        assertEquals(Blue80, colorScheme.primary)
        assertEquals(Color.White, colorScheme.secondary)
        assertEquals(Blue80, colorScheme.tertiary)
        assertEquals(Blue60, colorScheme.primaryContainer)
        assertEquals(Color.Black, colorScheme.onPrimary)
    }

    @Test
    fun defaultParameters_matchLightThemeAndDoNotUseDynamicColor() {
        var colorScheme: ColorScheme? = null
        rule.setContent {
            // No explicit darkTheme/dynamicColor args -- exactly how MainActivity calls this.
            PickFirstPlayerTheme {
                colorScheme = MaterialTheme.colorScheme
            }
        }

        assertEquals(Blue20, colorScheme?.primary)
    }

    private fun resolveColorScheme(darkTheme: Boolean): ColorScheme {
        var colorScheme: ColorScheme? = null
        rule.setContent {
            PickFirstPlayerTheme(darkTheme = darkTheme) {
                colorScheme = MaterialTheme.colorScheme
            }
        }
        return checkNotNull(colorScheme)
    }
}
