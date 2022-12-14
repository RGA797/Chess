package com.example.chess.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chess.viewModel.Game
import com.example.chess.viewModel.UiRemembrance

@Composable
fun Menu(navController: NavController) {
    Column() {
        Button(onClick = {navController.navigate(Screens.AiModePlayerWhite.route)
        } ,modifier = Modifier
            .height(100.dp)
            .width(100.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan),
            shape = RectangleShape
        )

        {
            Text(text = "white vs  black AI")
        }

        Button(onClick = {navController.navigate(Screens.AiModePlayerBlack.route)
        } ,modifier = Modifier
            .height(100.dp)
            .width(100.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan),
            shape = RectangleShape
        )

        {
            Text(text = "black vs  white AI")
        }
        Button(onClick = {navController.navigate(Screens.SandboxMode.route)
        } ,modifier = Modifier
            .height(100.dp)
            .width(100.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan),
            shape = RectangleShape
        ){
            Text(text = "Sandbox Mode")
        }
    }
}