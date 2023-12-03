import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.IntOffset
import problems.Solver


data class PartNumber(
    val position: IntOffset,
    val number: Int,
) {
    fun isValid(input: List<String>): Boolean {
        val yRange = ((position.y - 1).coerceAtLeast(0)..(position.y + 1).coerceAtMost(input.size - 1))
        val length = number.toString().length
        val xRange = ((position.x - 1).coerceAtLeast(0)..(position.x + length).coerceAtMost(input.size - 1))
        yRange.forEach { y ->
            xRange.forEach { x ->
                if (!input[y][x].isDigit() && input[y][x] != '.') return true
            }
        }
        return false
    }

    fun getGearCoordinates(input: List<String>): IntOffset? {
        val yRange = ((position.y - 1).coerceAtLeast(0)..(position.y + 1).coerceAtMost(input.size - 1))
        val length = number.toString().length
        val xRange = ((position.x - 1).coerceAtLeast(0)..(position.x + length).coerceAtMost(input.size - 1))
        yRange.forEach { y ->
            xRange.forEach { x ->
                if (!input[y][x].isDigit() && input[y][x] == '*') return IntOffset(x, y)
            }
        }
        return null
    }
}

class Day3Solver : Solver {
    override val input = useResource("input3") { stream ->
        stream.bufferedReader().readLines()
    }

    val partNumberCandidates = mutableStateListOf<PartNumber>()
    val partNumbers = mutableStateListOf<PartNumber>()

    fun loadParts(input: List<String>, partNumberCandidates: SnapshotStateList<PartNumber>) {
        input.forEachIndexed { y, line ->
            var processedNumber: Int? = null
            var processedNumberX = 0
            line.forEachIndexed { x, char ->
                if (char.isDigit()) {
                    if (processedNumber == null) {
                        processedNumber = char.digitToInt()
                        processedNumberX = x
                    } else {
                        processedNumber = processedNumber!! * 10 + char.digitToInt()
                    }
                } else {
                    if (processedNumber != null) {
                        partNumberCandidates.add(PartNumber(IntOffset(processedNumberX, y), processedNumber!!))
                        processedNumber = null
                    }
                }
            }
            if (processedNumber != null) {
                partNumberCandidates.add(PartNumber(IntOffset(processedNumberX, y), processedNumber!!))
                processedNumber = null
            }
        }
    }

    fun validateParts() {
        partNumbers.clear()
        partNumbers.addAll(partNumberCandidates.filter { it.isValid(input) }.map { PartNumber(it.position, it.number) })
    }

    override val solutionPart1 = mutableStateOf<Int?>(null)
    override val solutionPart2 = mutableStateOf<Int?>(null)

    override fun solvePart1(): Int {
        loadParts(input, partNumberCandidates)
        validateParts()
        return partNumbers.sumOf { it.number }.also { solutionPart1.value = it }
    }

    override fun solvePart2(): Int {
        val gearCandidates = partNumbers.map { it.getGearCoordinates(input) to it }.filter { it.first != null }
        val gears = gearCandidates.groupBy { it.first }.entries.filter { it.value.size >= 2 }
        val result = gears.sumOf { it.value[0].second.number * it.value[1].second.number }
        solutionPart2.value = result
        return result
    }
}
