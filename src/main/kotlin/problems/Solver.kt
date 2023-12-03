package problems

import androidx.compose.runtime.MutableState

interface Solver {
    val input: List<String>
    val solutionPart1: MutableState<Int?>
    val solutionPart2: MutableState<Int?>
    fun solvePart1(): Int
    fun solvePart2(): Int
}