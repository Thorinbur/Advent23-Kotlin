import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.useResource
import problems.Solver

class Day1Solver : Solver {
    override val input = useResource("input1") { stream ->
        stream.bufferedReader().readLines()
    }

    private enum class Digits(val int: Int) {
        Zero(0),
        One(1),
        Two(2),
        Three(3),
        Four(4),
        Five(5),
        Six(6),
        Seven(7),
        Eight(8),
        Nine(9),
    }

    private fun String.findSpelledDigit(spelledDigits: List<Pair<String, Int>>): Pair<Int, Int> {
        val digits = spelledDigits.map { digit -> indexOf(digit.first.lowercase()) to digit.second }
        return digits.filter { it.first != -1 }.takeIf { it.isNotEmpty() }?.minBy { it.first } ?: (Int.MAX_VALUE to 0)
    }

    private fun String.findDigit(spelledDigits: List<Pair<String, Int>>): Int {
        val firstDigitIndex = indexOfFirst { it.isDigit() }
        val (spelledDigitIndex, spelledDigit) = findSpelledDigit(spelledDigits)
        return if (firstDigitIndex < spelledDigitIndex) get(firstDigitIndex).toString().toInt() else spelledDigit
    }

    val spelledDigits = Digits.values().map { it.name.lowercase() to it.int }
    val spelledDigitsReversed = Digits.values().map { it.name.lowercase().reversed() to it.int }

    override val solutionPart1 = mutableStateOf<Int?>(null)
    override val solutionPart2 = mutableStateOf<Int?>(null)

    override fun solvePart1(): Int {
        val result = useResource("input1") { stream ->
            val lines = input.map {
                it.findDigit(emptyList()) * 10 + it.reversed().findDigit(emptyList())
            }
            lines.sumOf { it }
        }
        solutionPart1.value = result
        return result
    }

    override fun solvePart2(): Int {
        val result = useResource("input1") { stream ->
            val lines = input.map {
                it.findDigit(spelledDigits) * 10 + it.reversed().findDigit(spelledDigitsReversed)
            }
            lines.sumOf { it }
        }
        solutionPart2.value = result
        return result
    }
}
