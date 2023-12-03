package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import problems.Solver

@Composable
fun Day(solver: Solver) {
    MaterialTheme {
        Row {
            Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                Text("Input")
                Text(solver.input.joinToString(separator = "\n"), fontFamily = FontFamily.Monospace)
            }
            Column(modifier = Modifier.requiredWidth(200.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Button({
                    solver.solvePart1()
                    solver.solvePart2()
                }) {
                    Text("solve")
                }
                Text(
                    buildAnnotatedString {
                        append("Part 1:")
                        append((solver.solutionPart1.value?.toString() ?: ""))
                    }
                )
                Text("Part 2:" + (solver.solutionPart2.value?.toString() ?: ""))
            }
        }
    }
}