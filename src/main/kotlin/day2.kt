import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.useResource
import problems.Solver
import kotlin.math.max

private enum class CubeColor {
    Red, Blue, Green
}

private object Limits {
    val part1 = mapOf(
        CubeColor.Red to 12,
        CubeColor.Green to 13,
        CubeColor.Blue to 14,
    )
}

private data class CubeSet(
    var redCount: Int = 0,
    var greenCount: Int = 0,
    var blueCount: Int = 0,
) {
    fun updateMinimalSet(reveal: Reveal) {
        when (reveal.color) {
            CubeColor.Red -> redCount = max(redCount, reveal.count)
            CubeColor.Green -> greenCount = max(greenCount, reveal.count)
            CubeColor.Blue -> blueCount = max(blueCount, reveal.count)
        }
    }

    fun combine(other: CubeSet): CubeSet {
        return CubeSet(
            redCount = max(this.redCount, other.redCount),
            greenCount = max(this.greenCount, other.greenCount),
            blueCount = max(this.blueCount, other.blueCount),
        )
    }

    val power get() = redCount * greenCount * blueCount
}

private data class Reveal(
    val color: CubeColor,
    val count: Int
) {
    fun isPossible(limits: Map<CubeColor, Int>) = limits[color]!! >= count

    companion object {
        fun from(revealDescription: String): Reveal {
            val (count, color) = revealDescription.split(" ")
            return Reveal(CubeColor.valueOf(color.replaceFirstChar { it.uppercase() }), count.toInt())
        }
    }
}

private data class Round(
    val reveals: List<Reveal>
) {
    fun isPossible(limits: Map<CubeColor, Int>): Boolean {
        return reveals.all { it.isPossible(limits) }
    }

    val minimalCubeSet: CubeSet
        get() {
            return CubeSet().apply {
                reveals.forEach { updateMinimalSet(it) }
            }
        }

    companion object {
        fun from(roundDescription: String): Round {
            val reveals = roundDescription.split(",").map { Reveal.from(it.drop(1)) }
            return Round(reveals)
        }
    }
}

private data class Game(
    val number: Int,
    val rounds: List<Round>
) {
    fun isPossible(limits: Map<CubeColor, Int>): Boolean {
        return rounds.all { it.isPossible(limits) }
    }

    val power: Int
        get() = rounds.map { it.minimalCubeSet }.reduce { first, second -> first.combine(second) }.power

    companion object {
        private const val gameNamePrefix = "Game "
        fun from(gameDescription: String): Game {
            val (name, description) = gameDescription.split(":")
            val number = name.drop(gameNamePrefix.length).toInt()
            val roundsDescription = description.split(";")
            val rounds = roundsDescription.map { Round.from(it) }
            return Game(number, rounds)
        }
    }
}

class Day2Solver : Solver {
    override val input = useResource("input2") { stream ->
        stream.bufferedReader().readLines()
    }

    override val solutionPart1 = mutableStateOf<Int?>(null)
    override val solutionPart2 = mutableStateOf<Int?>(null)

    private val games = input.map { Game.from(it) }

    override fun solvePart1(): Int {
        val possibleGames = games.filter { it.isPossible(Limits.part1) }

        return possibleGames.sumOf { it.number }.also { solutionPart1.value = it }
    }

    override fun solvePart2(): Int {
        return games.sumOf { it.power }.also { solutionPart2.value = it }
    }
}
