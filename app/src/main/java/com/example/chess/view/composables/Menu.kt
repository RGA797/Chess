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

@Composable
fun Menu(navController: NavController) {
    Column() {
        Button(onClick = {navController.navigate(Screens.AiMode.route)
        } ,modifier = Modifier
            .height(100.dp)
            .width(100.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan),
            shape = RectangleShape
        ){
            Text(text = "Versus AI Mode")
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