package com.example.chess.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Menu(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Menu", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(100.dp))
        Button(onClick = {navController.navigate(Screens.AiMode.route)
        } ,modifier = Modifier
            .height(80.dp)
            .width(140.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan),
            shape = RectangleShape
        ){
            Text(text = "Versus AI Mode")
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {navController.navigate(Screens.SandboxMode.route)
        } ,modifier = Modifier
            .height(80.dp)
            .width(140.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan),
            shape = RectangleShape
        ){
            Text(text = "Sandbox Mode")
        }
    }
}