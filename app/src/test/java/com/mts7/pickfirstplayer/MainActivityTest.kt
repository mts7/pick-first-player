package com.mts7.pickfirstplayer

import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class MainActivityTest {
    @Test
    fun getRelationalValuesWithSixPlayersSelectOne() {
        val maxCount = 6
        val player = 1
        val expected = Pair(Direction.SELF, 0)

        val actual = getRelationalValues(maxCount, player)

        assertEquals(expected, actual)
    }

    @Test
    fun getRelationalValuesWithFivePlayersSelectThree() {
        val maxCount = 5
        val player = 3
        val expected = Pair(Direction.LEFT, 2)

        val actual = getRelationalValues(maxCount, player)

        assertEquals(expected, actual)
    }

    @Test
    fun getRelationalValuesWithFivePlayersSelectFour() {
        val maxCount = 5
        val player = 4
        val expected = Pair(Direction.RIGHT, 2)

        val actual = getRelationalValues(maxCount, player)

        assertEquals(expected, actual)
    }

    @Test
    fun getRelationalValuesWithTwoPlayersSelectTwo() {
        val maxCount = 2
        val player = 2
        val expected = Pair(Direction.OTHER, 1)

        val actual = getRelationalValues(maxCount, player)

        assertEquals(expected, actual)
    }

    @Test
    fun getRelationalWordingWithSelf() {
        val direction = Direction.SELF
        val places = 0

        val actual = getRelationalWording(direction, places)

        assertEquals("You go first.", actual)
    }

    @Test
    fun getRelationalWordingWithOther() {
        val direction = Direction.OTHER
        val places = 1

        val actual = getRelationalWording(direction, places)

        assertEquals("The other player goes first.", actual)
    }

    @Test
    fun getRelationalWordingWithOneToLeft() {
        val direction = Direction.LEFT
        val places = 1

        val actual = getRelationalWording(direction, places)

        assertEquals("The player on your left goes first.", actual)
    }

    @Test
    fun getRelationalWordingWithTwoToLeft() {
        val direction = Direction.LEFT
        val places = 2

        val actual = getRelationalWording(direction, places)

        assertEquals("The player 2 to your left goes first.", actual)
    }

    @Test
    fun getRelationalWordingWithThreeToRight() {
        val direction = Direction.RIGHT
        val places = 3

        val actual = getRelationalWording(direction, places)

        assertEquals("The player 3 to your right goes first.", actual)
    }

    @Test
    fun getRelationalWordingWithTwoToRight() {
        val direction = Direction.RIGHT
        val places = 2

        val actual = getRelationalWording(direction, places)

        assertEquals("The player 2 to your right goes first.", actual)
    }

    @Test
    fun getRelationalWordingWithOneToRight() {
        val direction = Direction.RIGHT
        val places = 1

        val actual = getRelationalWording(direction, places)

        assertEquals("The player on your right goes first.", actual)
    }
}
