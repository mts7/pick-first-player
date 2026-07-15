package com.mts7.pickfirstplayer

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class ThemeConsolidationTest {
    private val themeSource =
        File("src/main/java/com/mts7/pickfirstplayer/ui/theme/Theme.kt").readText()
    private val mainActivitySource =
        File("src/main/java/com/mts7/pickfirstplayer/MainActivity.kt").readText()

    @Test
    fun theme_defaultsToCustomBrandColorsNotDynamicColor() {
        assertTrue(
            "PickFirstPlayerTheme's dynamicColor parameter should default to false so the " +
                "app's custom Blue color palette is used instead of Android 12+ " +
                "wallpaper-derived Material You colors, which was overriding it silently.",
            themeSource.contains("dynamicColor: Boolean = false")
        )
    }

    @Test
    fun mainActivity_doesNotImportRawColorConstants() {
        assertFalse(
            "MainActivity should reference MaterialTheme.colorScheme instead of importing " +
                "raw Blue20/Blue60/Blue80 color constants directly, so there is one source " +
                "of truth for the app's palette.",
            mainActivitySource.contains("import com.mts7.pickfirstplayer.ui.theme.Blue20") ||
                mainActivitySource.contains("import com.mts7.pickfirstplayer.ui.theme.Blue60") ||
                mainActivitySource.contains("import com.mts7.pickfirstplayer.ui.theme.Blue80")
        )
    }
}
