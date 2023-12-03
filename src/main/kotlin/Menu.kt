import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainMenu(goToSolution: (Int) -> Unit) {
    Column() {
        (1..3).forEach {
            Row(Modifier.clickable { goToSolution(it) }) {
                Text("Day $it", Modifier.weight(1f))
                Text("⭐⭐", color = Color.Yellow)
            }
            Spacer(modifier = Modifier.height(1.dp).background(Color.DarkGray))
        }
    }
}