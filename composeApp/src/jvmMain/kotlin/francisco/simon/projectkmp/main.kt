package francisco.simon.projectkmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import francisco.simon.projectkmp.app.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ProjectKmp",
    ) {
        App()
    }
}
