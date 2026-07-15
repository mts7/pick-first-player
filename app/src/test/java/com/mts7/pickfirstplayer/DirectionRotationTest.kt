package com.mts7.pickfirstplayer

import org.junit.Assert.assertEquals
import org.junit.Test

class DirectionRotationTest {
    @Test
    fun left_isZeroDegrees() {
        assertEquals(0.0F, rotationFor(Direction.LEFT))
    }

    @Test
    fun self_is270Degrees() {
        assertEquals(270.0F, rotationFor(Direction.SELF))
    }

    @Test
    fun other_is90Degrees() {
        assertEquals(90.0F, rotationFor(Direction.OTHER))
    }

    @Test
    fun right_is180Degrees() {
        assertEquals(180.0F, rotationFor(Direction.RIGHT))
    }
}
