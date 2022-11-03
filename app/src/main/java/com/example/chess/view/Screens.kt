package com.example.chess.view

sealed class Screens(val route: String) {
    object Menu: Screens("Menu")
    object AiMode: Screens("Ai_mode")
    object SandboxMode: Screens("Sandbox_Mode")
}