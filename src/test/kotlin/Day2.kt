import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test() {
    @Test
    fun testPart1() {
        val day2 = Day2Solver()

        val result = day2.solvePart1()

        assertEquals(3099, result)
    }

    @Test
    fun testPart2() {
        val day2 = Day2Solver()

        val result = day2.solvePart2()

        assertEquals(72970, result)
    }
}