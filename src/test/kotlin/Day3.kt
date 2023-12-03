import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Test() {
    @Test
    fun testPart1() {
        val day3 = Day3Solver()

        val result = day3.solvePart1()

        assertEquals(554003, result)
    }

    @Test
    fun testPart2() {
        val day3 = Day3Solver()

        day3.solvePart1()
        val result = day3.solvePart2()

        assertEquals(87263515, result)
    }
}