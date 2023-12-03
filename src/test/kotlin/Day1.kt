import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test() {
    @Test
    fun testPart1() {
        val day1 = Day1Solver()

        day1.solvePart1()

        assertEquals(56042, day1.solutionPart1.value)
    }

    @Test
    fun testPart2() {
        val day1 = Day1Solver()

        day1.solvePart2()

        assertEquals(55358, day1.solutionPart2.value)
    }
}