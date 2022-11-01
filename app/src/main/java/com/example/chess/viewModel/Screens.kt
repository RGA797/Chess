package com.example.chess.viewModel

sealed class Screens(val route: String) {
    object menu: Screens("Menu")
    object AiMode: Screens("AiMode")
    object SandboxMode: Screens("SandboxMode")
}