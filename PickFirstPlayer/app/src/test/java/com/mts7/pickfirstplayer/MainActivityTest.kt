package com.mts7.pickfirstplayer

import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class MainActivityTest {
    @Test
    fun generateRandomNumberReturnsAValidNumber() {
        val maxValue = 3
        val number = generateRandomNumber(maxValue)

        assert(number >= 1)
        assert(number <= maxValue)
    }

    @Test
    fun getRelationalWordingWith6PlayersChoosing1() {
        val maxValue = 6
        val player = 1

        val actual = getRelationalWording(maxCount = maxValue, player = player)

        assertEquals("You go first.", actual)
    }

    @Test
    fun getRelationalWordingWith6PlayersChoosing2() {
        val maxValue = 6
        val player = 2

        val actual = getRelationalWording(maxCount = maxValue, player = player)

        assertEquals("The player 1 to your left goes first.", actual)
    }

    @Test
    fun getRelationalWordingWith6PlayersChoosing3() {
        val maxValue = 6
        val player = 3

        val actual = getRelationalWording(maxCount = maxValue, player = player)

        assertEquals("The player 2 to your left goes first.", actual)
    }

    @Test
    fun getRelationalWordingWith6PlayersChoosing4() {
        val maxValue = 6
        val player = 4

        val actual = getRelationalWording(maxCount = maxValue, player = player)

        assertEquals("The player 3 to your right goes first.", actual)
    }

    @Test
    fun getRelationalWordingWith6PlayersChoosing5() {
        val maxValue = 6
        val player = 5

        val actual = getRelationalWording(maxCount = maxValue, player = player)

        assertEquals("The player 2 to your right goes first.", actual)
    }

    @Test
    fun getRelationalWordingWith6PlayersChoosing6() {
        val maxValue = 6
        val player = 6

        val actual = getRelationalWording(maxCount = maxValue, player = player)

        assertEquals("The player 1 to your right goes first.", actual)
    }
}
