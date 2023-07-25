package com.mts7.pickfirstplayer

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
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
            ButtonGrid(
                numbers = listOf(2, 3, 4, 5, 6, 7),
                onNumberClick = {}
            )
        }

        rule.onAllNodes(hasClickAction())
            .assertCountEquals(6)
    }
//
//    @Test
//    fun buttonGrid_cannotHaveFiveNumbers() {
//        rule.setContent {
//            ButtonGrid(
//                numbers = listOf(2, 4, 5, 6, 7),
//                onNumberClick = {}
//            )
//        }
//
//        rule.onAllNodes(hasClickAction())
//            .assertCountEquals(6)
//    }
}