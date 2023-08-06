package com.mts7.pickfirstplayer

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import com.mts7.pickfirstplayer.ui.theme.PickFirstPlayerTheme
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun numberButton_click() {
        var clicked = false
        rule.setContent { NumberButton(5, onNumberClick = { clicked = true }) }

        rule.onNode(hasText("5"))
            .assertHasClickAction()
            .performClick()

        assert(clicked)
    }

    @Test
    fun buttonGrid_hasSixNumberButtons() {
        rule.setContent {
            PickFirstPlayerTheme {
                ButtonGrid(
                    onNumberClick = {}
                )
            }
        }

        rule.onNode(hasText("2"))
            .assertExists()
            .assertHasClickAction()
        rule.onNode(hasText("3"))
            .assertExists()
            .assertHasClickAction()
        rule.onNode(hasText("4"))
            .assertExists()
            .assertHasClickAction()
        rule.onNode(hasText("5"))
            .assertExists()
            .assertHasClickAction()
        rule.onNode(hasText("6"))
            .assertExists()
            .assertHasClickAction()
        rule.onNode(hasText("7"))
            .assertExists()
            .assertHasClickAction()
        rule.onAllNodes(hasClickAction())
            .assertCountEquals(6)
    }

    @Test
    fun chosenValue_displaysText() {
        val maxValue = 7
        rule.setContent { ChosenValue(maxValue) }

        rule.onNode(
            hasText("Number of players: $maxValue")
        )
            .assertExists()
    }

    @Test
    fun bottomBar_doesNotHaveResetButton() {
        rule.setContent {
            BottomBar(onExit = {}, displayReset = false, onResetClick = {})
        }

        rule.onNode(
            hasText("Reset")
        )
            .assertDoesNotExist()
    }

    @Test
    fun bottomBar_hasResetButton() {
        rule.setContent {
            BottomBar(onExit = {}, displayReset = true, onResetClick = {})
        }

        rule.onNode(
            hasText("Reset")
        )
            .assertExists()
    }

    @Test
    fun bottomBar_clickReset() {
        var clicked = false
        rule.setContent {
            BottomBar(
                onExit = {},
                displayReset = true,
                onResetClick = { clicked = true })
        }

        rule.onNode(hasText("Reset")).assertHasClickAction().performClick()

        assert(clicked)
    }

    @Test
    fun bottomBar_clickExit() {
        var clicked = false
        rule.setContent {
            BottomBar(
                onExit = { clicked = true },
                displayReset = true,
                onResetClick = {})
        }

        rule.onNode(hasText("Exit")).assertHasClickAction().performClick()

        assert(clicked)
    }

    @Test
    fun playerDirection_rotationLeft() {
        val direction = "left"
        val places = 2
        rule.setContent { PlayerDirection(direction, places, refreshSelection = {}) }

        rule.onNode(hasContentDescription("Back arrow", true))
            .assertExists()

        // TODO: test the rotation float value for all three directions
    }

    @Test
    fun playerDirection_placesZero() {
        val direction = "self"
        val places = 0
        rule.setContent { PlayerDirection(direction, places, refreshSelection = {}) }

        rule.onNode(hasText("$places"))
            .assertDoesNotExist()
    }

    @Test
    fun playerDirection_placesOneOther() {
        val direction = "other"
        val places = 1
        rule.setContent { PlayerDirection(direction, places, refreshSelection = {}) }

        rule.onNode(hasText("The other player goes first."))
            .assertExists()
    }

    @Test
    fun playerDirection_placesTwo() {
        val direction = "right"
        val places = 2
        rule.setContent { PlayerDirection(direction, places, refreshSelection = {}) }

        rule.onNode(
            hasText("$places") and hasParent(
                hasAnyChild(
                    hasContentDescription(
                        "Back arrow",
                        true
                    )
                )
            )
        )
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun playerDirection_hasWording() {
        val direction = "left"
        val places = 1
        rule.setContent { PlayerDirection(direction, places, refreshSelection = {}) }

        rule.onNode(hasText("The player on your $direction goes first."))
            .assertExists()
    }
}