package com.example.chess.view

sealed class Screens(val route: String) {
    object Menu: Screens("Menu")
    object AiModePlayerBlack: Screens("White_AI_mode")
    object AiModePlayerWhite: Screens("Black_AI_mode")
    object SandboxMode: Screens("Sandbox_Mode")
}