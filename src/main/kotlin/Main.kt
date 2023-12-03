import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.Day

sealed class Screen {
    object Menu : Screen()
    class Solution(val day: Int) : Screen()
}

@Composable
@Preview
fun App(screen: Screen, changeScreen: (Screen) -> Unit) {
    Box(modifier = Modifier.padding(16.dp)) {
        val solution = screen as? Screen.Solution
        if (screen is Screen.Menu) {
            MainMenu { changeScreen(Screen.Solution(it)) }
        } else if (solution != null) {
            when (solution.day) {
                1 -> Day(Day1Solver())
                2 -> Day(Day2Solver())
                3 -> Day(Day3Solver())
            }
        }
    }
}

fun main() = application {
    var screen by remember { mutableStateOf<Screen>(Screen.Menu) }

    val windowState = rememberWindowState()
    Window(onCloseRequest = ::exitApplication, transparent = true, undecorated = true, state = windowState) {
        MaterialTheme(colors = darkColors()) {
            Scaffold(
                topBar = {
                    WindowDraggableArea {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            if (screen != Screen.Menu) {
                                Button(onClick = { screen = Screen.Menu }) { Text("<") }
                            }
                            Spacer(modifier = Modifier.weight(1f).background(Color.DarkGray))
                            Button(onClick = { this@application.exitApplication() }) { Text("x") }
                        }
                    }
                }
            ) {
                App(screen) { screen = it }
            }
        }
    }
}
