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
    fun getRelationalValuesWithSixPlayersSelectOne() {
        val maxCount = 6
        val player = 1
        val expected = Pair("self", 0)

        val actual = getRelationalValues(maxCount, player)

        assertEquals(expected, actual)
    }

    @Test
    fun getRelationalValuesWithFivePlayersSelectThree() {
        val maxCount = 5
        val player = 3
        val expected = Pair("left", 2)

        val actual = getRelationalValues(maxCount, player)

        assertEquals(expected, actual)
    }

    @Test
    fun getRelationalValuesWithFivePlayersSelectFour() {
        val maxCount = 5
        val player = 4
        val expected = Pair("right", 2)

        val actual = getRelationalValues(maxCount, player)

        assertEquals(expected, actual)
    }

    @Test
    fun getRelationalWordingWithSelf() {
        val direction = "self"
        val places = 0

        val actual = getRelationalWording(direction, places)

        assertEquals("You go first.", actual)
    }

    @Test
    fun getRelationalWordingWithOneToLeft() {
        val direction = "left"
        val places = 1

        val actual = getRelationalWording(direction, places)

        assertEquals("The player on your left goes first.", actual)
    }

    @Test
    fun getRelationalWordingWithTwoToLeft() {
        val direction = "left"
        val places = 2

        val actual = getRelationalWording(direction, places)

        assertEquals("The player 2 to your left goes first.", actual)
    }

    @Test
    fun getRelationalWordingWithThreeToRight() {
        val direction = "right"
        val places = 3

        val actual = getRelationalWording(direction, places)

        assertEquals("The player 3 to your right goes first.", actual)
    }

    @Test
    fun getRelationalWordingWithTwoToRight() {
        val direction = "right"
        val places = 2

        val actual = getRelationalWording(direction, places)

        assertEquals("The player 2 to your right goes first.", actual)
    }

    @Test
    fun getRelationalWordingWithOneToRight() {
        val direction = "right"
        val places = 1

        val actual = getRelationalWording(direction, places)

        assertEquals("The player on your right goes first.", actual)
    }

//    @ParameterizedTest(name = "getRelationalWording")
//    @MethodSource("getRelationalWordingData")
//    fun `relational wording should match as indicated`(data: RelationalWordingData) {
//        val actual = getRelationalWording(data.direction, data.places)
//
//        assertEquals(data.expected, actual)
//    }
//
//    private companion object {
//        @JvmStatic
//        fun getRelationalWordingData(): Stream<RelationalWordingData> = Stream.of(
//            RelationalWordingData(
//                direction = "self",
//                places = 0,
//                expected = "You go first."
//            ),
//            RelationalWordingData(
//                direction = "left",
//                places = 1,
//                expected = "The player on your left goes first."
//            ),
//            RelationalWordingData(
//                direction = "right",
//                places = 1,
//                expected = "The player on your right goes first."
//            ),
//            RelationalWordingData(
//                direction = "left",
//                places = 2,
//                expected = "The player 2 to your left goes first."
//            ),
//            RelationalWordingData(
//                direction = "right",
//                places = 2,
//                expected = "The player 2 to your right goes first."
//            ),
//        )
//    }
//
//    data class RelationalWordingData(
//        val direction: String,
//        val places: Int,
//        val expected: String,
//    )
}
