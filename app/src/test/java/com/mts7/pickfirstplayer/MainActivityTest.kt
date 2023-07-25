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
        val direction = "self"
        val places = 0

        val actual = getRelationalWording(direction, places)

        assertEquals("You go first.", actual)
    }

    @Test
    fun getRelationalWordingWith6PlayersChoosing2() {
        val direction = "left"
        val places = 1

        val actual = getRelationalWording(direction, places)

        assertEquals("The player on your left goes first.", actual)
    }

    @Test
    fun getRelationalWordingWith6PlayersChoosing3() {
        val direction = "left"
        val places = 2

        val actual = getRelationalWording(direction, places)

        assertEquals("The player 2 to your left goes first.", actual)
    }

    @Test
    fun getRelationalWordingWith6PlayersChoosing4() {
        val direction = "right"
        val places = 3

        val actual = getRelationalWording(direction, places)

        assertEquals("The player 3 to your right goes first.", actual)
    }

    @Test
    fun getRelationalWordingWith6PlayersChoosing5() {
        val direction = "right"
        val places = 2

        val actual = getRelationalWording(direction, places)

        assertEquals("The player 2 to your right goes first.", actual)
    }

    @Test
    fun getRelationalWordingWith6PlayersChoosing6() {
        val direction = "right"
        val places = 1

        val actual = getRelationalWording(direction, places)

        assertEquals("The player on your right goes first.", actual)
    }
}
