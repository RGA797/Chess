package com.example.chess.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chess.viewModel.Game
import com.example.chess.viewModel.UiRemembrance

@Composable
fun Navigation(){
    val navController = rememberNavController()
    val game = Game
    val ui = UiRemembrance()
    NavHost(navController = navController, startDestination = Screens.Menu.route){
        composable(Screens.Menu.route){
            Menu(navController = navController)
        }
        composable(Screens.AiModePlayerWhite.route){
            AiModePlayerWhite(game, ui)
        }
        composable(Screens.AiModePlayerBlack.route){
            AiModePlayerBlack(game, ui)
        }
        composable(Screens.SandboxMode.route){
            SandboxMode(game, ui)
        }
    }
}
